package com.googlecode.wmbutil.messages;

import com.google.common.base.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.googlecode.wmbutil.util.ElementUtil;
import com.googlecode.wmbutil.util.TypeSafetyHelper;
import com.googlecode.wmbutil.util.XmlUtil;
import com.ibm.broker.plugin.*;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Helper class for working with XML elements.
 *
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
@SuppressWarnings("unused")
public class XmlElement extends MbElementWrapper {
    /**
     * @param wrappedElm The XML element
     */
    public XmlElement(MbElement wrappedElm) {
        super(wrappedElm);
    }

    /**
     * Gets the element name
     *
     * @return Element name
     * @throws MbException
     */
    public String getName() throws MbException {
        return getMbElement().getName();
    }

    /**
     * Gets the element namespace
     *
     * @return Element namespace
     * @throws MbException
     */
    public String getNameSpace() throws MbException {
        return getMbElement().getNamespace();
    }

    /**
     * Checks if the element has an attribute of the specified name
     *
     * @param name Attribute name
     * @return True if the attribute headerExistsIn
     * @throws MbException
     */
    public boolean hasAttribute(String name) throws MbException {
        return hasAttribute(Optional.<String>absent(), name);
    }

    public boolean hasAttribute(String ns, String name) throws MbException {
        return hasAttribute(Optional.of(ns), name);
    }

    /**
     * Checks if the element has an attribute of the specified name and namespace
     *
     * @param ns   Attribute namespace
     * @param name Attribute name
     * @return True if the attribute headerExistsIn
     * @throws MbException
     */
    public boolean hasAttribute(Optional<String> ns, String name) throws MbException {
        return getAttributeElement(checkNotNull(ns), checkNotNull(name)) != null;
    }

    private Optional<MbElement> getAttributeElement(String name) throws MbException {
        return getAttributeElement(Optional.<String>absent(), name);
    }

    /**
     * Gets an attribute of the specified name and namespace.
     * Only returns the first attribute as multiple attributes
     * with the same name is invalid XML
     *
     * @param ns   Attribute namespace
     * @param name Attribute name
     * @return An attribute element
     * @throws MbException
     */
    private Optional<MbElement> getAttributeElement(String ns, String name) throws MbException {
        return getAttributeElement(Optional.of(ns), name);
    }

    private Optional<MbElement> getAttributeElement(Optional<String> ns, String name) throws MbException {
        return Iterables.tryFind(getAttributeElements(ns, attributeName(ns, name)), Predicates.alwaysTrue());
    }

    private List<MbElement> getAttributeElements() throws MbException {
        return getAttributeElements(Optional.<String>absent(), Predicates.<MbElement>alwaysTrue());
    }

    private List<MbElement> getAttributeElements(String ns) throws MbException {
        return getAttributeElements(Optional.of(ns));
    }

    private List<MbElement> getAttributeElements(Optional<String> ns) throws MbException {
        return getAttributeElements(ns, Predicates.<MbElement>alwaysTrue());
    }

    private List<MbElement> getAttributeElements(Predicate<MbElement> predicate) throws MbException {
        return getAttributeElements(Optional.<String>absent(), predicate);
    }

    private List<MbElement> getAttributeElements(String ns, Predicate<MbElement> predicate) throws MbException {
        return getAttributeElements(Optional.of(ns), predicate);
    }

    /**
     * Gets all attributes. Attribute name and/or namespace can also be specified to
     * filter wanted attributes
     *
     * @param ns        Attribute(s) namespace
     * @param predicate Predicate to filter matches
     * @return All found attribute elements
     * @throws MbException
     */
    private List<MbElement> getAttributeElements(Optional<String> ns, Predicate<MbElement> predicate) throws MbException {
        checkNotNull(ns);
        checkNotNull(predicate);

        MbXPath xpath = new MbXPath("@*", getMbElement());

        if (ns.isPresent()) {
            xpath.setDefaultNamespace(ns.get());
        }

        return ImmutableList.copyOf(
                Iterables.filter(
                        TypeSafetyHelper.typeSafeList((List<?>) getMbElement().evaluateXPath(xpath), MbElement.class),
                        Predicates.and(Predicates.notNull(), predicate)));
    }

    private static Predicate<MbElement> attributeName(final String name) {
        return attributeName(Optional.<String>absent(), name);
    }

    private static Predicate<MbElement> attributeName(final String ns, final String name) {
        return attributeName(Optional.of(ns), name);
    }

    private static Predicate<MbElement> attributeName(final Optional<String> ns, final String name) {
        checkNotNull(name);
        checkNotNull(ns);
        return new Predicate<MbElement>() {
            // WMB prepends a @ on undefined MRM attributes
            final String atName = "@" + name;

            private boolean matchLocalName(MbElement input) throws MbException {
                return name.equals(input.getName()) || atName.equals(input.getName());
            }

            private boolean matchNamespace(MbElement input) throws MbException {
                return ns.get().equals(input.getNamespace());
            }

            @Override
            public boolean apply(MbElement input) {
                try {
                    return matchLocalName(input) && (!ns.isPresent() || matchNamespace(input));
                } catch (MbException e) {
                    return false;
                }
            }
        };
    }

    /**
     * Gets an attribute of the specified name
     *
     * @param name Attribute name
     * @return The attribute, if found
     * @throws MbException
     */
    public Optional<String> getAttribute(String name) throws MbException {
        return getAttribute(Optional.<String>absent(), name);
    }

    /**
     * Gets an attribute of the specified name & namespace
     *
     * @param ns   Attribute namespace
     * @param name Attribute name
     * @return The attribute, if found
     * @throws MbException
     */
    public Optional<String> getAttribute(Optional<String> ns, String name) throws MbException {
        Optional<MbElement> attr = getAttributeElement(checkNotNull(ns), checkNotNull(name));
        return attr.isPresent() ?
                Optional.fromNullable(attr.get().getValueAsString()) : Optional.<String>absent();
    }

    /**
     * Sets an attribute of the specified name
     * The attribute is automatically created if needed
     *
     * @param name  Attribute name
     * @param value Attribute value
     * @throws MbException
     */
    public void setAttribute(String name, String value) throws MbException {
        setAttribute(Optional.<String>absent(), name, value);
    }

    public void setAttribute(String ns, String name, String value) throws MbException {
        setAttribute(Optional.of(ns), name, value);
    }

    /**
     * Sets an attribute of the specified name & namspace
     *
     * @param ns    Attribute namespace
     * @param name  Attribute name
     * @param value Attribute value
     * @throws MbException
     */
    public void setAttribute(Optional<String> ns, String name, String value) throws MbException {
        checkNotNull(value);
        Optional<MbElement> attr = getAttributeElement(checkNotNull(ns), checkNotNull(name));
        if (attr.isPresent()) {
            MbElement element = getMbElement()
                    .createElementAsFirstChild(XmlUtil.getAttributeType(getMbElement()), name, value);
            if (ns.isPresent()) {
                element.setNamespace(ns.get());
            }
        } else {
            attr.get().setValue(value);
        }
    }

    /**
     * Gets the names of all attributes
     *
     * @return The names of all attributes
     * @throws MbException
     */
    public String[] getAttributeNames() throws MbException {
        return getAttributeNames(Optional.<String>absent());
    }

    /**
     * Gets the names of all attributes of the specified namespace
     *
     * @param ns Attribute namespace
     * @return The names of all attributes
     * @throws MbException
     */
    public String[] getAttributeNames(Optional<String> ns) throws MbException {
        return Iterables.toArray(
                Lists.transform(getAttributeElements(checkNotNull(ns)), new Function<MbElement, String>() {
                    @Override
                    public String apply(MbElement input) {
                        String name;
                        try {
                            name = input.getName();
                        } catch (MbException e) {
                            throw Throwables.propagate(e);
                        }
                        if (name.charAt(0) == '@') {
                            name = name.substring(1);
                        }
                        return name;
                    }
                }), String.class);
    }

    /**
     * Creates a new element as the last child using the specified name
     *
     * @param name Element name
     * @return The newly created element
     * @throws MbException
     */
    public XmlElement createLastChild(String name) throws MbException {
        return createLastChild(Optional.<String>absent(), name);
    }

    public XmlElement createLastChild(String ns, String name) throws MbException {
        return createLastChild(Optional.of(ns), name);
    }

    /**
     * Creates a new element as the last child using the specified name & namespace
     *
     * @param ns   Element namespace
     * @param name Element name
     * @return The newly created element
     * @throws MbException
     */
    public XmlElement createLastChild(Optional<String> ns, String name) throws MbException {
        MbElement elm = getMbElement().createElementAsLastChild(
                XmlUtil.getFolderElementType(getMbElement()), checkNotNull(name), null);
        if (ns.isPresent()) {
            elm.setNamespace(ns.get());
        }
        return new XmlElement(elm);
    }

    /**
     * Gets all children elements of the specified name
     *
     * @param name Children element(s) name
     * @return All found elements, if any
     * @throws MbException
     */
    public List<XmlElement> getChildrenByName(String name) throws MbException {
        return getChildrenByName(Optional.<String>absent(), name);
    }

    public List<XmlElement> getChildrenByName(String ns, String name) throws MbException {
        return getChildrenByName(Optional.of(ns), name);
    }

    /**
     * Gets all children elements of the specified name & namespace
     *
     * @param ns   Children element(s) namespace
     * @param name Children element(s) name
     * @return All found elements
     * @throws MbException
     */
    public List<XmlElement> getChildrenByName(Optional<String> ns, String name) throws MbException {
        MbXPath xpath = new MbXPath(checkNotNull(name), getMbElement());

        if (ns.isPresent()) {
            xpath.setDefaultNamespace(ns.get());
        }

        List<MbElement> children =
                TypeSafetyHelper.typeSafeList((List<?>) getMbElement().evaluateXPath(xpath), MbElement.class);

        // TODO: Possible Immutable Exception -- Check usage of tSL
        return Lists.transform(children, new Function<MbElement, XmlElement>() {
            @Override
            public XmlElement apply(@Nullable MbElement input) {
                return new XmlElement(input);
            }
        });
    }

    /**
     * Gets the first child element
     *
     * @return The first child element
     * @throws MbException
     */
    public XmlElement getFirstChild() throws MbException {
        return new XmlElement(getMbElement().getFirstChild());
    }

    /**
     * Gets the first child element of the specified name
     *
     * @param name Child element name
     * @return The first child element
     * @throws MbException
     */
    public XmlElement getFirstChildByName(String name) throws MbException {
        return getFirstChildByName(Optional.<String>absent(), Optional.of(name));
    }

    public XmlElement getFirstChildByName(String ns, String name) throws MbException {
        return getFirstChildByName(Optional.of(ns), Optional.of(name));
    }

    /**
     * Gets the first child element of the specified name & namespace
     *
     * @param ns   Child element namespace
     * @param name Child element name
     * @return The first child element
     * @throws MbException
     */
    public XmlElement getFirstChildByName(Optional<String> ns, Optional<String> name) throws MbException {
        MbXPath xpath = new MbXPath(name.or("*") + "[1]", getMbElement());

        if (ns.isPresent()) {
            xpath.setDefaultNamespace(ns.get());
        }

        List<MbElement> childList = TypeSafetyHelper.typeSafeList
                ((List<?>) getMbElement().evaluateXPath(xpath), MbElement.class);

        return childList.isEmpty() ? null : new XmlElement(childList.get(0));
    }

    /**
     * Gets the value as a date
     *
     * @return Element value as a date
     * @throws MbException
     */
    public Date getDateValue() throws MbException {
        final Object value = getValue().orNull();
        if (value == null) {
            return null;
        } else if (value instanceof MbTimestamp) {
            return ((MbTimestamp) value).getTime();
        } else if (value instanceof MbDate) {
            return ((MbDate) value).getTime();
        } else if (value instanceof MbTime) {
            return ((MbTime) value).getTime();
        } else {
            throw new ClassCastException("Type can not be cast to a date type: " + value.getClass());
        }
    }

    @Override
    public <T> void setValue(String field, T value) throws MbException {
        if (ElementUtil.isMRM(getMbElement())) {
            super.setValue(field, value);
        } else {
            super.setValue(field, value.toString());
        }
    }

    @Override
    public void setValue(Object value) throws MbException {
        if (ElementUtil.isMRM(getMbElement())) {
            super.setValue(value);
        } else {
            super.setValue(value.toString());
        }
    }

    /**
     * Sets the time value of a date with the specified dateformat
     *
     * @param value Date element value
     * @param df    Value dateformat
     * @throws MbException
     */
    public void setTimeValue(Date value, DateFormat df) throws MbException {
        setValue(df.format(value));
    }

    /**
     * Sets the time value of a date.
     *
     * @param value Date element value
     * @throws MbException
     */
    public void setTimeValue(Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        setValue(new MbTime(
                cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND)));
    }

    /**
     * Sets the value of a date with the specified dateformat
     *
     * @param value Date element value
     * @param df    Value dateformat
     * @throws MbException
     */
    public void setDateValue(Date value, DateFormat df) throws MbException {
        setValue(df.format(value));
    }

    /**
     * Sets the value of a date
     *
     * @param value Date element value
     * @throws MbException
     */
    public void setDateValue(Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        setValue(new MbDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));
    }

    /**
     * Sets the value of a date with the specified dateformat.
     *
     * @param value Date element value
     * @param df    Value dateformat
     * @throws MbException
     */
    public void setTimestampValue(Date value, DateFormat df) throws MbException {
        setValue(df.format(value));
    }

    /**
     * Sets the timestamp value of a date
     *
     * @param value Date element value
     * @throws MbException
     */
    public void setTimestampValue(Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        setValue(new MbTimestamp(
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
                cal.get(Calendar.MILLISECOND)));
    }
}
