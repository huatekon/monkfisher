package org.huatekon.utilities;

import org.powerbot.core.randoms.SpinTickets;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

public final class ClaimSpinTickets extends SpinTickets{

	@Override
	public void execute() {
		if (((Settings.get(1448) & 0xFF00) >>> 8) < 10) {
			final Item item = Inventory.getItem(SpinTickets.ITEM_ID_SPIN_TICKET);
			if (item != null && item.getWidgetChild().interact("Claim spin")) {
				sleep(1000);
			}
		} else {
			super.execute();
		}
	}
}
