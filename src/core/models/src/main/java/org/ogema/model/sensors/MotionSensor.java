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
package org.ogema.model.sensors;

import org.ogema.core.model.ModelModifiers.NonPersistent;
import org.ogema.core.model.simple.BooleanResource;
import org.ogema.model.ranges.BinaryRange;
import org.ogema.model.targetranges.BinaryTargetRange;

/**
 * motion detector
 */
public interface MotionSensor extends GenericBinarySensor {

	/**
	 * motion status (non-persistent)<br>
	 * true: motion detected. false: no motion detected
	 */
	@NonPersistent
	@Override
	BooleanResource reading();

	/**
	 * Possible values for motion status (non-persistent)<br>
	 * true: motion detected. false: no motion detected
	 */
	@Override
	BinaryRange ratedValues();

	/**
	 * Settings for motion status for EM-system.<br>
	 * true: motion detected. false: no motion detected
	 */
	@Override
	BinaryTargetRange settings();

	/**
	 * Settings for motion status to be sent to the device.<br>
	 * true: motion detected. false: no motion detected
	 */
	@Override
	BinaryTargetRange deviceSettings();

	/**
	 * Device settings feedback for motion status.<br>
	 * true: motion detected. false: no motion detected
	 */
	@Override
	BinaryTargetRange deviceFeedback();
}
