/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2008-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="monitored-services")
public class OnmsMonitoredServiceDetailList extends LinkedList<OnmsMonitoredServiceDetail> {

    private static final long serialVersionUID = 8031737923157780179L;
    private int m_totalCount;

    /**
     * <p>Constructor for OnmsMonitoredServiceDetailList.</p>
     */
    public OnmsMonitoredServiceDetailList() {
        super();
    }

    /**
     * <p>Constructor for OnmsMonitoredServiceDetailList.</p>
     *
     * @param c a {@link java.util.Collection} object.
     */
    public OnmsMonitoredServiceDetailList(Collection<? extends OnmsMonitoredServiceDetail> c) {
        super(c);
    }

    /**
     * <p>getServices</p>
     *
     * @return a {@link java.util.List} object.
     */
    @XmlElement(name="monitored-service")
    public List<OnmsMonitoredServiceDetail> getServices() {
        return this;
    }

    /**
     * <p>setServices</p>
     *
     * @param services a {@link java.util.List} object.
     */
    public void setServices(List<OnmsMonitoredServiceDetail> services) {
        if (services == this) return;
        clear();
        addAll(services);
    }

    /**
     * <p>getCount</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    @XmlAttribute(name="count")
    public Integer getCount() {
        return this.size();
    }

    /**
     * <p>getTotalCount</p>
     *
     * @return a int.
     */
    @XmlAttribute(name="totalCount")
    public int getTotalCount() {
        return m_totalCount;
    }
    
    /**
     * <p>setTotalCount</p>
     *
     * @param count a int.
     */
    public void setTotalCount(int count) {
        m_totalCount = count;
    }

}
