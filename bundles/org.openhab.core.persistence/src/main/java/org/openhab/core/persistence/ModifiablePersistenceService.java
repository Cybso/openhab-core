/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.core.persistence;

import java.time.ZonedDateTime;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.items.Item;
import org.openhab.core.types.State;

/**
 * This class provides an interface to the a {@link PersistenceService} to allow data to be stored
 * at a specific time. This allows bindings that interface to devices that store data internally,
 * and then periodically provide it to the server to be accommodated.
 *
 * @author Chris Jackson - Initial contribution
 */
@NonNullByDefault
public interface ModifiablePersistenceService extends QueryablePersistenceService {
    /**
     * <p>
     * Stores the historic item value. This allows the item, time and value to be specified.
     *
     * <p>
     * Adding data with the same time as an existing record should update the current record value rather than adding a
     * new record.
     *
     * <p>
     * Implementors should keep in mind that all registered {@link PersistenceService}s are called synchronously. Hence
     * long running operations should be processed asynchronously. E.g. <code>store</code> adds things to a queue which
     * is processed by some asynchronous workers (Quartz Job, Thread, etc.).
     *
     * @param item the data to be stored
     * @param date the date of the record
     * @param state the state to be recorded
     */
    void store(Item item, ZonedDateTime date, State state);

    /**
     * <p>
     * Stores the historic item value under a specified alias. This allows the item, time and value to be specified.
     *
     * <p>
     * Adding data with the same time as an existing record should update the current record value rather than adding a
     * new record.
     *
     * <p>
     * Implementors should keep in mind that all registered {@link PersistenceService}s are called synchronously. Hence
     * long running operations should be processed asynchronously. E.g. <code>store</code> adds things to a queue which
     * is processed by some asynchronous workers (Quartz Job, Thread, etc.).
     *
     * @param item the data to be stored
     * @param date the date of the record
     * @param state the state to be recorded
     */
    void store(Item item, ZonedDateTime date, State state, @Nullable String alias);

    /**
     * Removes data associated with an item from a persistence service.
     * If all data is removed for the specified item, the persistence service should free any resources associated with
     * the item (e.g. remove any tables or delete files from the storage).
     *
     * @param filter the filter to apply to the data removal. ItemName can not be null.
     * @return true if the query executed successfully
     * @throws IllegalArgumentException if item name is null.
     */
    boolean remove(FilterCriteria filter) throws IllegalArgumentException;
}
