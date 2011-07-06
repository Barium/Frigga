/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.itu.frigga.action;

import dk.itu.frigga.action.filter.*;
import dk.itu.frigga.device.Device;
import dk.itu.frigga.utility.XmlHelper;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Mikkel Wendt-Larsen (miwe@itu.dk)
 * @author Tommy Andersen (toan@itu.dk)
 */
public class Rule
{
    private String description;
    private String id;
    private final VariableContainer variableContainer = new VariableContainer();
    private final ActionContainer actionContainer = new ActionContainer();
    private final ConditionContainer conditionContainer;
    private final ReplacementContainer replacementContainer;
    private final FilterFactory filterFactory;
    private final Set<Device> validDevices = Collections.synchronizedSet(new LinkedHashSet<Device>());

    public Rule(final FilterFactory factory, final ReplacementContainer replacementContainer)
    {
        this.conditionContainer = new ConditionContainer();
        this.replacementContainer = replacementContainer;
        this.filterFactory = factory;
    }

    public void run(final FilterContext context) throws FilterFailedException
    {
        FilterOutput output = context.run(conditionContainer.getRootFilter());

        Collection<Device> validated = output.matchingDevices();

    }

    public String getDescription()
    {
        return description;
    }

    public String getId()
    {
        return id;
    }

    public void parse(final Element element) throws FilterSyntaxErrorException
    {
        if (element == null) throw new IllegalArgumentException("Argument 'element' is null");

        if (!element.hasAttribute("id")) throw new FilterSyntaxErrorException();
        id = element.getAttribute("id");

        if (element.hasAttribute("description")) description = element.getAttribute("description");

        for (Element elem = XmlHelper.getFirstChildElement(element); elem != null; elem = XmlHelper.getNextSiblingElement(elem))
        {
            if (elem.getTagName().equals("variables"))
            {
                variableContainer.parse(elem);
            }

            else if (elem.getTagName().equals("actions"))
            {
                actionContainer.parse(elem);
            }

            else if (elem.getTagName().equals("condition"))
            {
                conditionContainer.parse(elem, filterFactory);
            }
        }
    }
}