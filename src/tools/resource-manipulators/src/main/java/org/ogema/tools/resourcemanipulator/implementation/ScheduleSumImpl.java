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
package org.ogema.tools.resourcemanipulator.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.ogema.tools.resourcemanipulator.ResourceManipulatorImpl;
import org.ogema.core.model.Resource;
import org.ogema.core.model.schedule.Schedule;
import org.ogema.core.model.simple.FloatResource;
import org.ogema.tools.resourcemanipulator.configurations.ScheduleSum;
import org.ogema.tools.resourcemanipulator.model.ScheduleSumModel;

/**
 * Implementation for a schedule sum configuration.
 * 
 * @author Timo Fischer, Fraunhofer IWES
 */
public class ScheduleSumImpl implements ScheduleSum {

	private final ResourceManipulatorImpl m_base;
	private List<Schedule> m_inputs;
	private Schedule m_output;
	private long m_delay;
	private boolean m_deactivateEmtpySum;

	// Configuration this is connected to (null if not connected)
	private ScheduleSumModel m_config;

	/**
	 * Creates an instance of the configuration object from an existing configuration.
	 */
	public ScheduleSumImpl(ResourceManipulatorImpl base, ScheduleSumModel configResource) {
		m_base = base;
        m_inputs = new ArrayList<>(configResource.inputs().getAllElements());
        m_output = configResource.resultBase().program();
        m_delay = configResource.delay().getValue();
        m_deactivateEmtpySum = configResource.deactivateEmptySum().getValue();
		m_config = configResource;
	}

	public ScheduleSumImpl(ResourceManipulatorImpl base) {
		m_base = base;
		m_inputs = null;
		m_output = null;
		m_delay = 0;
		m_deactivateEmtpySum = false;
		m_config = null;
	}

	@Override
	public boolean commit() {
		if (m_inputs == null || m_output == null) {
			return false;
		}

		// Check that all schedules are of type FloatValue
		for (Schedule schedule : m_inputs) {
			final Resource parent = schedule.getParent();
			if (parent == null)
				return false;
			if (!(parent instanceof FloatResource))
				return false;
		}

		// delete the old configuration if it exsited.
		if (m_config != null)
			m_config.delete();

		m_config = m_base.createResource(ScheduleSumModel.class);

		m_config.inputs().create();
		for (Schedule schedule : m_inputs) {
			m_config.inputs().add(schedule);
		}
		FloatResource resultBase = m_config.resultBase().create();
		resultBase.program().setAsReference(m_output);
		m_config.delay().create();
		m_config.delay().setValue(m_delay);
		m_config.deactivateEmptySum().create();
		m_config.deactivateEmptySum().setValue(m_deactivateEmtpySum);

		m_config.activate(true);
		return true;
	}

	@Override
	public void remove() {
		if (m_config != null) {
			m_config.delete();
		}
	}

	@Override
    public void setAddends(Collection<Schedule> addends, Schedule sum) {
        m_inputs = new ArrayList<>(addends);
        m_output = sum;
        m_delay = 0;
    }

	@Override
	public void setDelay(long delayTime) {
		m_delay = delayTime;
	}

	@Override
	public long getDelay() {
		return m_delay;
	}

	@Override
	public Schedule getTarget() {
		return m_output;
	}

	@Override
	public List<Schedule> getAddends() {
		return Collections.unmodifiableList(m_inputs);
	}

	@Override
	public void setDisableEmptySum(boolean emptySumDisables) {
		m_deactivateEmtpySum = emptySumDisables;
	}

	@Override
	public boolean getDisableEmptySum() {
		return m_deactivateEmtpySum;
	}

}
