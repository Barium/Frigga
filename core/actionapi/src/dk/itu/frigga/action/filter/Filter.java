package dk.itu.frigga.action.filter;

import dk.itu.frigga.action.runtime.Selection;
import dk.itu.frigga.utility.XmlHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.util.*;

/**
 * Class description here...
 *
 * @author Tommy Andersen (toan@itu.dk)
 * @version 1.00, 2011-06-01
 */
public abstract class Filter
{
    protected FilterContainer filterContainer;
    protected List<Filter> childFilters = new LinkedList<Filter>();

    private String name = "";
    private List<FilterListener> filterListeners = Collections.synchronizedList(new LinkedList<FilterListener>());

    protected void setId(final String name)
    {
        final String oldName = this.name;
        this.name = name;

        for (FilterListener filterListener : filterListeners)
        {
            filterListener.FilterNameChanged(this, oldName, name);
        }
    }

    public boolean hasId()
    {
        return !name.isEmpty();
    }

    public String getId()
    {
        return name;
    }

    public void setContainer(final FilterContainer container)
    {
        filterContainer = container;

        if (!name.isEmpty())
        {
            try
            {
                filterContainer.registerNamedFilter(this);
            }
            catch (NotANamedFilterException e)
            {
                // Apparently this isn't a named filter, so no point in adding it.
            }
        }
    }

    public final void parse(final FilterFactory factory, final Element element)
    {
        if (factory == null) throw new IllegalArgumentException("Argument 'factory' is null");
        if (element == null) throw new IllegalArgumentException("Argument 'element' is null");

        if (element.hasAttribute("id"))
        {
            setId(element.getAttribute("id"));
        }

        final Map<String, String> attributes = new HashMap<String, String>();
        NamedNodeMap map = element.getAttributes();
        for (int i = 0; i < map.getLength(); i++)
        {
            final Attr attr = (Attr)map.item(i);
            final String name = attr.getName();

            if (!name.equals("id"))
            {
                attributes.put(name, attr.getValue());
            }
        }

        loadFilter(attributes);

        if (element.hasChildNodes() && allowChildFilters())
        {
            Element elem = XmlHelper.getFirstChildElement(element);

            while (elem != null)
            {
                Element el = elem;
                boolean allowed = true;

                // Handle constraints, these allow us to have version specific blocks in our filters.
                while (allowed && factory.isConstraint(el))
                {
                    allowed = factory.constraintPassed(el);
                    if (allowed)
                    {
                        el = XmlHelper.getFirstChildElement(el);
                    }
                }

                // el can be null if we have a constraint that passes evaluation but does not contain any child filters
                if (allowed && el != null)
                {
                    try
                    {
                        childFilters.add(factory.createFilter(filterContainer, el));
                    }
                    catch (UnknownFilterException e)
                    {
                        // The filter type was not known, fail gracefully.
                    }
                    catch (FilterInstantiationFailedException e)
                    {
                        // The filter did instantiate properly, fail gracefully.
                    }
                }

                elem = XmlHelper.getNextSiblingElement(elem);
            }
        }
    }

    protected void loadFilter(final Map<String, String> attributes)
    {
        // Overriding this function will allow custom filter attribute handling
    }

    public void addFilterListener(final FilterListener listener)
    {
        if (listener == null) throw new IllegalArgumentException("Argument 'listener' is null");

        filterListeners.add(listener);
    }

    public void removeFilterListener(final FilterListener listener)
    {
        if (listener == null) throw new IllegalArgumentException("Argument 'listener' is null");

        filterListeners.remove(listener);
    }

    public abstract List<Selection> run(final FilterContext context) throws FilterFailedException;

    protected abstract boolean allowChildFilters();
}