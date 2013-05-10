package org.huatekon.monkfisher.nodes;

import org.huatekon.monkfisher.utilities.Methods;
import org.huatekon.utilities.Condition;
import org.huatekon.utilities.Strategy;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Fish implements Strategy {

	private final int FISH_SPOT = 3848;
	private final Area LEFT_SHORE = new Area(new Tile(2335, 3700, 0), new Tile(
			2320, 3698, 0));
	private final Area RIGHT_SHORE = new Area(new Tile(2347, 3700, 0),
			new Tile(2336, 3697, 0));

	@Override
	public boolean validate() {
		return Players.getLocal().getAnimation() != 621 && !Inventory.isFull();
	}

	@Override
	public void execute() {
		NPC fishspot = NPCs.getNearest(FISH_SPOT);
		if (fishspot != null) {
			if (!fishspot.isOnScreen()) {
				Walking.walk(fishspot);
				Task.sleep(500);
			}

			if (Bank.isOpen())
				Bank.close();

			if (fishspot.interact("Net")) {
				Methods.waitFor(new Condition() {

					@Override
					public boolean validate() {
						return Players.getLocal().getAnimation() == 621;
					}
				}, 5000);

				sleepWhileFishing();
			}
		} else {
			lookForSpots();
		}
	}

	/**
	 * It waits until player stop fishing
	 */
	private void sleepWhileFishing() {
		while (Players.getLocal() != null) {
			if (Players.getLocal().getAnimation() == 621) {
				Task.sleep(200);
				Methods.antiban();
			}
			break;
		}
	}

	/**
	 * It searches for fishing spots along the shore.
	 */
	private void lookForSpots() {
		if (Players.getLocal().getLocation().distance(LEFT_SHORE.getNearest()) < Players
				.getLocal().getLocation().distance(RIGHT_SHORE.getNearest())) {
			Walking.walk(RIGHT_SHORE.getNearest());
			Task.sleep(1000, 1600);
		} else {
			Walking.walk(LEFT_SHORE.getNearest());
			Task.sleep(1000, 1600);
		}
	}

}
