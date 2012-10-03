/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2011 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2011 The OpenNMS Group, Inc.
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
package org.opennms.features.vaadin.datacollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.opennms.features.vaadin.mibcompiler.api.Logger;
import org.opennms.netmgt.config.datacollection.DatacollectionConfig;
import org.opennms.netmgt.config.datacollection.Rrd;
import org.opennms.netmgt.config.datacollection.SnmpCollection;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class SNMP Collection Panel.
 * 
 * @author <a href="mailto:agalue@opennms.org">Alejandro Galue</a> 
 */
@SuppressWarnings("serial")
public class SnmpCollectionPanel extends VerticalLayout {

    /** The form. */
    private final SnmpCollectionForm form;

    /** The table. */
    private final SnmpCollectionTable table;

    /** The add button. */
    private final Button add;

    /** The isNew flag. True, if the resource type is new. */
    private boolean isNew;

    /**
     * Instantiates a new SNMP collection panel.
     *
     * @param dataCollectionConfig the data collection config
     * @param logger the logger
     */
    public SnmpCollectionPanel(final DatacollectionConfig dataCollectionConfig, final Logger logger) {
        form = new SnmpCollectionForm() {
            @Override
            public void saveSnmpCollection(SnmpCollection snmpCollection) {
                if (isNew) {
                    table.addSnmpCollection(snmpCollection);
                    logger.info("SNMP Collection " + snmpCollection.getName() + " has been created.");
                } else {
                    logger.info("SNMP Collection " + snmpCollection.getName() + " has been updated.");
                }
                table.refreshRowCache();
            }
            @Override
            public void deleteSnmpCollection(SnmpCollection snmpCollection) {
                logger.info("SNMP Collection " + snmpCollection.getName() + " has been removed.");
                table.removeItem(snmpCollection.getName());
                table.refreshRowCache();
            }
        };

        table = new SnmpCollectionTable(dataCollectionConfig) {
            @Override
            public void updateExternalSource(BeanItem<SnmpCollection> item) {
                form.setItemDataSource(item, Arrays.asList(SnmpCollectionForm.FORM_ITEMS));
                form.setVisible(true);
                form.setReadOnly(true);
                setIsNew(false);
            }
        };

        add = new Button("Add Resource Type", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                SnmpCollection collection = new SnmpCollection();
                collection.setName("New Collection");
                collection.setSnmpStorageFlag("select");
                Rrd rrd = new Rrd();
                rrd.setStep(300);
                rrd.addRra("RRA:AVERAGE:0.5:1:2016");
                rrd.addRra("RRA:AVERAGE:0.5:12:1488");
                rrd.addRra("RRA:AVERAGE:0.5:288:366");
                rrd.addRra("RRA:MAX:0.5:288:366");
                rrd.addRra("RRA:MIN:0.5:288:366");
                table.updateExternalSource(new BeanItem<SnmpCollection>(collection));
                form.setReadOnly(false);
                setIsNew(true);
            }
        });

        setSpacing(true);
        setMargin(true);
        addComponent(table);
        addComponent(add);
        addComponent(form);

        setComponentAlignment(add, Alignment.MIDDLE_RIGHT);
    }

    /**
     * Gets the SNMP collections.
     *
     * @return the SNMP collections
     */
    @SuppressWarnings("unchecked")
    public Collection<SnmpCollection> getSnmpCollections() {
        final Collection<SnmpCollection> collections = new ArrayList<SnmpCollection>();
        for (Object itemId : table.getContainerDataSource().getItemIds()) {
            collections.add(((BeanItem<SnmpCollection>)table.getContainerDataSource().getItem(itemId)).getBean());
        }
        return collections;
    }

    /**
     * Sets the value of the ifNew flag.
     *
     * @param isNew true, if the resource type is new.
     */
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
}