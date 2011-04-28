package dk.itu.frigga.protocol;

/**
 * Class description here...
 *
 * @author Tommy Andersen (toan@itu.dk)
 * @version 1.00, 2011-04-12
 */
public interface ActionReceivedListener
{
    public void requestsReceived(final MessageSource source, final MessageResult result, final Requests requests);
}