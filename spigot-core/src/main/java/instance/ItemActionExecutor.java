package instance;

import item.ClickHandler;

public interface ItemActionExecutor {

    void execute(ClickHandler clickHandler, String label, String[] args);
}
