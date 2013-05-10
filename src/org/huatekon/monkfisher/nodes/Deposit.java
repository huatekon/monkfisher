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
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Deposit implements Strategy {

	private final int BANK = 3824;
	private final Area BANK_AREA = new Area(new Tile(2334, 3694, 0), new Tile(
			2326, 3685, 0));
	private final Area BANK_LOCATIONS = new Area(new Tile(2331, 3691, 0),
			new Tile(2328, 3687, 0));
	private final Tile[] bank_locations = BANK_LOCATIONS.getTileArray();

	@Override
	public boolean validate() {
		return Inventory.isFull();
	}

	@Override
	public void execute() {
		Methods.dropGiftBox();
		if (!BANK_AREA.contains(Players.getLocal().getLocation())) {
			Walking.walk(bank_locations[Random
					.nextInt(0, bank_locations.length)]);
			if (BANK_AREA.contains(Walking.getDestination())) {
				Methods.waitFor(new Condition() {

					@Override
					public boolean validate() {
						return BANK_AREA.contains(Players.getLocal()
								.getLocation());
					}
				}, 5000);
			}
		}

		NPC bank = NPCs.getNearest(BANK);
		if (bank != null) {

			if (Bank.isOpen())
				Bank.depositInventory();

			else {
				Methods.openBank(bank);
				Bank.depositInventory();
			}
		} else {
			Walking.walk(bank_locations[Random
					.nextInt(0, bank_locations.length)]);
			Task.sleep(500, 2500);
		}
	}

}
