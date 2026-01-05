package fr.uge.poo.logger.q0;

public class FilterLogger implements Logger {
    private final Logger logger;
    private final Level min;
    private final Level max;

    public FilterLogger(Logger logger, Level min, Level max) {
        this.logger = logger;
        this.min = min;
        this.max = max;
    }

    @Override
    public void log(Level level, String message) {
        if(level.ordinal() >= min.ordinal() && level.ordinal() <= max.ordinal()){
            logger.log(level, message);
        }
    }


}
