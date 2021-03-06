/**
 * This file is part of OGEMA.
 *
 * OGEMA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation.
 *
 * OGEMA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OGEMA. If not, see <http://www.gnu.org/licenses/>.
 */
package org.ogema.channelmanager.impl;

import org.ogema.core.channelmanager.ChannelConfiguration;
import org.ogema.core.channelmanager.ChannelConfiguration.Direction;
import org.ogema.core.channelmanager.driverspi.SampledValueContainer;
import org.ogema.core.channelmanager.measurements.SampledValue;

public class Channel {
	private final ChannelConfiguration channelConfig;
	private SampledValueContainer value = null;

	public Channel(ChannelConfiguration channelConfig) throws NullPointerException {
		if (channelConfig == null) {
			throw new NullPointerException();
		}
		this.channelConfig = channelConfig;
		this.value = new SampledValueContainer(channelConfig.getChannelLocator());

	}

	public ChannelConfiguration getConfiguration() {
		return channelConfig;
	}

	public SampledValue getValue() {
		return value.getSampledValue();
	}

	public void setValue(SampledValue value) {
		this.value.setSampledValue(value);
	}

	public SampledValueContainer getSampledValueContainer() {
		return value;
	}

	public boolean isWritable() {
		if (channelConfig.getDirection() == Direction.DIRECTION_INPUT) {
			return false;
		}
		else {
			return true;
		}
	}
}
