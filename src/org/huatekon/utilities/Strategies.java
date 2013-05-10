package org.huatekon.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: kyle
 * Date: 23/09/2012
 * Time: 2:54 PM
 */

public class Strategies {
    private static List<Strategy> strategyList = new ArrayList<Strategy>();

    private static void add(Strategy strategy) {
        strategyList.add(strategy);
    }

    /**
     * @param strategies strategies to add to current list
     */
    public static void add(Strategy... strategies) {
        for (Strategy strategy : strategies) {
            add(strategy);
        }
    }

    private static void remove(Strategy strategy) {
        strategyList.remove(strategy);
    }

    /**
     * @param strategies strategies to remove from current list
     */
    public static void remove(Strategy... strategies) {
        for (Strategy strategy : strategies) {
            remove(strategy);
        }
    }

    /**
     * @return returns list of strategies
     */
    public static Strategy[] get() {
        return strategyList.toArray(new Strategy[strategyList.size()]);
    }
}
