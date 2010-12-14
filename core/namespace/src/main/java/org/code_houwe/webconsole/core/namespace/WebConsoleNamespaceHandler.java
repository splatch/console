package org.code_houwe.webconsole.core.namespace;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.aries.blueprint.NamespaceHandler;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.aries.blueprint.mutable.MutableCollectionMetadata;
import org.apache.aries.blueprint.mutable.MutableIdRefMetadata;
import org.apache.aries.blueprint.mutable.MutableRefMetadata;
import org.apache.aries.blueprint.mutable.MutableServiceMetadata;
import org.apache.aries.blueprint.mutable.MutableValueMetadata;
import org.code_house.webconsole.core.api.plugin.Plugin;
import org.code_house.webconsole.core.api.resource.Resource;
import org.code_house.webconsole.core.api.resource.ResourceCollection;
import org.code_house.webconsole.core.common.plugin.PluginImpl;
import org.code_house.webconsole.core.common.resource.ResourceCollectionImpl;
import org.code_house.webconsole.core.common.resource.ResourceImpl;
import org.code_house.webconsole.core.namespace.model.ObjectFactory;
import org.code_house.webconsole.core.namespace.model.TExtension;
import org.code_house.webconsole.core.namespace.model.TPlugin;
import org.code_house.webconsole.core.namespace.model.TResource;
import org.code_house.webconsole.core.namespace.model.TResourceCollection;
import org.osgi.service.blueprint.container.ComponentDefinitionException;
import org.osgi.service.blueprint.reflect.CollectionMetadata;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.IdRefMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.osgi.service.blueprint.reflect.RefMetadata;
import org.osgi.service.blueprint.reflect.ValueMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WebConsoleNamespaceHandler implements NamespaceHandler {

    private Logger logger = LoggerFactory.getLogger(WebConsoleNamespaceHandler.class);

    private Map<Class, Class> elementMap = new HashMap<Class, Class>();
    private Map<Class, Class> serviceMap = new HashMap<Class, Class>();

    public WebConsoleNamespaceHandler() {
        map(TPlugin.class, Plugin.class, PluginImpl.class);
        map(TResource.class, Resource.class, ResourceImpl.class);
        map(TResourceCollection.class, ResourceCollection.class, ResourceCollectionImpl.class);
    }

    private void map(Class elementType, Class apiType,
        Class defaultImpl) {
        elementMap.put(elementType, defaultImpl);
        serviceMap.put(elementType, apiType);
        logger.debug("Add element {} to implementation {}, service {}", new Object[] {
            elementType.getName(), defaultImpl.getName(), apiType.getName()});
    }

    public URL getSchemaLocation(String namespace) {
        return getClass().getResource("/webconsole-1.0.xsd");
    }

    public Set<Class> getManagedClasses() {
        HashSet<Class> managedClasses = new HashSet<Class>();
        managedClasses.addAll(elementMap.values());
        managedClasses.addAll(serviceMap.values());
        logger.debug("Managed classes{}", managedClasses);
        return managedClasses;
    }

    public Metadata parse(Element element, ParserContext context) {
        if ("plugin".equals(element.getNodeName())) {
            try {
                JAXBContext jsc = JAXBContext.newInstance(ObjectFactory.class);
                Unmarshaller unmr = jsc.createUnmarshaller();
                TPlugin plugin = unmr.unmarshal(element, TPlugin.class).getValue();

                return parsePlugin(plugin, context);
            } catch (JAXBException e) {
                throw new ComponentDefinitionException("Cannot create plugin", e);
            }
        }
        throw new ComponentDefinitionException("Unsuported element "
            + element.getNodeName());
    }

    private Metadata parsePlugin(TPlugin item, ParserContext context) {
        MutableBeanMetadata metadata = createBean(item, context);

        addParameter("name", item.getName(), context, metadata);
        addParameter("vendor", item.getVendor(), context, metadata);
        addParameter("url", item.getUrl(), context, metadata);

        for (TExtension extension : item.getResourceOrResourceCollection()) {
            if (extension instanceof TResource) {
                parseResource((TResource) extension, context);
            } else if (extension instanceof TResourceCollection) {
                parseResourceCollection((TResourceCollection) extension, context);
            }
        }

        return metadata;
    }

    private MutableBeanMetadata createBean(TExtension extension, ParserContext context) {
        MutableBeanMetadata metadata = context.createMetadata(MutableBeanMetadata.class);
        metadata.setId(extension.getId());
        // map XSD element to default implementation
        metadata.setClassName(elementMap.get(extension.getClass()).getName());
        addParameter("id", extension.getId(), context, metadata);

        logger.debug("Create bean {} of type {}", metadata.getId(), metadata.getClassName());

        MutableServiceMetadata service = createService(extension, context);
        service.setServiceComponent(createRef(context, extension.getId()));
        service.addDependsOn(extension.getId());
        if (!(extension instanceof TPlugin)) {
            // plugin will be registered in blueprint after returning metadata 
            // from parse method.
            context.getComponentDefinitionRegistry().registerComponentDefinition(
                metadata);
            logger.debug("Register bean {}", metadata.getId());
        }
        context.getComponentDefinitionRegistry().registerComponentDefinition(
            service);

        return metadata;
    }

    private void addParameter(String property, String value, ParserContext context,
        MutableBeanMetadata metadata) {
        metadata.addProperty(property, createValue(context, value));
    }

    private MutableServiceMetadata createService(TExtension extension, ParserContext context) {
        MutableServiceMetadata metadata = context.createMetadata(MutableServiceMetadata.class);
        Class serviceInterface = serviceMap.get(extension.getClass());
        metadata.addInterface(serviceInterface.getName());

        metadata.addServiceProperty(createValue(context, "webExtension"),
            createValue(context, "true"));
        metadata.addServiceProperty(createValue(context, "extensionType"),
            createValue(context, serviceInterface.getName()));
        metadata.addServiceProperty(createValue(context, "extensionId"),
            createValue(context, extension.getId()));
        metadata.setId(extension.getId() + "Service");
        return metadata;
    }

    private Metadata parseResource(TResource item, ParserContext context) {
        MutableBeanMetadata metadata = createBean(item, context);
        addParameter("alias", item.getAlias(), context, metadata);
        addParameter("location", item.getLocation(), context, metadata);

        metadata.addProperty("bundle", createIdRef(context, "blueprintBundle"));
        return metadata;
    }

    private Metadata parseResourceCollection(TResourceCollection item, ParserContext context) {
        MutableBeanMetadata metadata = createBean(item, context);
        addParameter("aliasPrefix", item.getAliasPrefix(), context, metadata);
        addParameter("location", item.getLocation(), context, metadata);

        metadata.addProperty("bundle", createRef(context, "blueprintBundle"));
        return metadata;
    }

    public ComponentMetadata decorate(Node node, ComponentMetadata component,
        ParserContext context) {
        // decoration is not supported
        return null;
    }

    //
    private static ValueMetadata createValue(ParserContext context, String value) {
        return createValue(context, value, null);
    }

    private static ValueMetadata createValue(ParserContext context,
        String value, String type) {
        MutableValueMetadata m = context
            .createMetadata(MutableValueMetadata.class);
        m.setStringValue(value);
        m.setType(type);
        return m;
    }

    private static RefMetadata createRef(ParserContext context, String value) {
        MutableRefMetadata m = context.createMetadata(MutableRefMetadata.class);
        m.setComponentId(value);
        return m;
    }

    private static IdRefMetadata createIdRef(ParserContext context, String value) {
        MutableIdRefMetadata m = context
            .createMetadata(MutableIdRefMetadata.class);
        m.setComponentId(value);
        return m;
    }

    private static CollectionMetadata createList(ParserContext context,
        List<String> list) {
        MutableCollectionMetadata m = context
            .createMetadata(MutableCollectionMetadata.class);
        m.setCollectionClass(List.class);
        m.setValueType(String.class.getName());
        for (String v : list) {
            m.addValue(createValue(context, v, String.class.getName()));
        }
        return m;
    }

    private static String getTextValue(Element element) {
        StringBuffer value = new StringBuffer();
        NodeList nl = element.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node item = nl.item(i);
            if ((item instanceof CharacterData && !(item instanceof Comment))
                || item instanceof EntityReference) {
                value.append(item.getNodeValue());
            }
        }
        return value.toString();
    }

}
