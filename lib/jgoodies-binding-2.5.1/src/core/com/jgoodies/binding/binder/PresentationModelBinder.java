/*
 * Copyright (c) 2002-2011 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jgoodies.binding.binder;



/**
 * Adds binding capabilities for bean property names to its superclass
 * that can bind ValueModels, SelectionInLists, Actions, and
 * ListModel + ListSelectionModel. Note that you can bind properties
 * of the presentation model's bean (#bindBeanProperty) as well as
 * properties of the presentation model itself (#bindProperty).
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.1 $
 *
 * @since 2.3
 */
public interface PresentationModelBinder extends BeanBinder {


    /**
     * Creates and returns a binding builder that manages a ValueModel
     * that can be operated on and that can be bound to a component.<p>
     *
     * <strong>Example:</strong>
     * <tt>binder.<b>bindBeanProperty</b>("artist").to(artistField);</tt>
     *
     * @param propertyName     the name of the property
     *     of the presentation model's bean
     *
     * @return the binding builder that holds the converted bean property
     *
     * @throws NullPointerException if {@code propertyName} is {@code null}
     */
    ValueModelBindingBuilder bindBeanProperty(String propertyName);

}

