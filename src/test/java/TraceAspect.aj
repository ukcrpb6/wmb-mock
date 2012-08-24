import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public aspect TraceAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    pointcut traced()
        : execution(* *.*(..)) && !within(TraceAspect);

    before() : traced() {
        Signature sig = thisJoinPoint.getStaticPart().getSignature();
        logger.trace("Entering [" + sig.toShortString() + "]");
    }

    after() : traced() {
        Signature sig = thisJoinPoint.getStaticPart().getSignature();
        logger.trace("Leaving [" + sig.toShortString() + "]");
    }
}
