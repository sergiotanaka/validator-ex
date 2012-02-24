/*
 * Copyright (c) 2011 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * This software is the proprietary information of JGoodies Karsten Lentzsch.
 * Use is subject to license terms.
 *
 */

package com.jgoodies.binding.binder;



/**
 * Adds binding capabilities for Action names to its super interface
 * that can already bind Action instances.<p>
 *
 * The JGoodies Binding library doesn't ship an implementation for this
 * interface, because there's no popular and public standard that describes
 * <em>and</em> implements how to look up an Action for a name.
 * A popular approach is the JSR 296 (Swing Application Framework)
 * that enables developers to annotate methods with <code>&#x40;Action</code>
 * that then can be requested for a target object and an action name.<p>
 *
 * Since the JGoodies implementation of the JSR 296 (the JGoodies
 * "Application" library) is not available to the general public,
 * the JGoodies Application-based implementation of this interface
 * is not part of the Binding library.<p>
 *
 * However, this interface reduce the effort to integrate your custom
 * Action-lookup and binder mechanism that may be based on the JSR 296 or
 * any other Action name registry.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.1 $
 */
public interface ActionObjectBinder extends ObjectBinder {


    /**
     * Looks up an Action for this binder and the given action name.
     * Then creates and returns a binding builder that manages an Action that
     * can be operated on and that can be bound to a button or text field.<p>
     *
     * <strong>Examples:</strong><br>
     * <tt>binder.<b>bindAction</b>("edit")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.to(editButton);</tt><br>
     * <tt>binder.<b>bindAction</b>(ACTION_EDIT).to(editButton);</tt><p>
     *
     * Implementations will typically look up an Action for a given action name
     * and hand it over to {@link ObjectBinder#bind(javax.swing.Action)}.
     *
     * @param actionName     the name of the Action to be bound
     *
     * @return the binding builder that holds the Action
     *
     * @throws NullPointerException  if {@code actionName} if {@code null}
     */
    ActionBindingBuilder bindAction(String actionName);


}

