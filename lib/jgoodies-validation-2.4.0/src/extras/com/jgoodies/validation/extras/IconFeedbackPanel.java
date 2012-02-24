/*
 * Copyright (c) 2003-2012 JGoodies Karsten Lentzsch. All Rights Reserved.
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

package com.jgoodies.validation.extras;

import static com.jgoodies.common.base.Preconditions.checkNotNull;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.jgoodies.validation.view.ValidationResultViewFactory;

/**
 * Can display validation feedback icons "over" a content panel.
 * It observes a ValidationResultModel and creates icon labels
 * in a feedback layer of a {@link JLayeredPane} on top of the content layer.
 * To position the feedback labels, the content pane is traversed
 * and searched for text components that match a validation message key
 * in this panel's observed ValidationResultModel.<p>
 *
 * <strong>Note:</strong> This panel doesn't reserve space for the portion
 * used to display the overlaid feedback components. It has been designed
 * to not change the layout of the wrapped content. Therefore you must reserve
 * this space, or in other words, you must ensure that the wrapped content
 * provides enough space to display the overlaid components.
 * Since the current implementation positions the overlay components
 * in the lower left, just make sure that there are about 6 pixel to the left
 * and bottom of the input components that can be marked.<p>
 *
 * This panel handles two event types: <ol>
 * <li>the ValidationResultModel changes; in this case the set of visible
 * feedback components shall mark the input components that match the
 * new validation result. This is done by this class' internal
 * {@code ValidationResultChangeHandler} which in turn invokes
 * {@code #updateFeedbackComponents}.
 * <li>the content layout changes; the feedback components must then be
 * repositioned to reflect the position of the overlaid input components.
 * This is done by overriding {@code #validateTree} and invoking
 * {@code #repositionFeedBackComponents} after the child tree has
 * been laid out. The current simple but expensive implementation
 * updates all components.
 * </ol><p>
 *
 * TODO: Check how the wrapping mechanism shall work with
 * JSplitPanes, JTabbedPanes and CardPanels. At least provide
 * guidelines, how to wrap these panel types, or how to handle
 * these cases.<p>
 *
 * TODO: Turn this class into an abstract superclass.
 * Subclasses shall implement the feedback component creation
 * and specify where to locate the feedback component relative
 * to the underlying content component.<p>
 *
 * TODO: Consider adding a mechanism, so that components can be added
 * and removed later.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.2 $
 */
public final class IconFeedbackPanel extends JLayeredPane {

    private static final int CONTENT_LAYER  = 1;
    private static final int FEEDBACK_LAYER = 2;

    /**
     * Holds the ValidationResult and reports changes in that result.
     * Used to update the state of the feedback components.
     */
    private final ValidationResultModel model;

    /**
     * Refers to the content panel that holds the content components.
     */
    private final JComponent content;


    // Instance Creation ******************************************************

    /**
     * Creates an IconFeedbackPanel on the given ValidationResultModel
     * using the specified content panel.<p>
     *
     * <strong>Note:</strong> Typically you should wrap component trees with
     * {@link #getWrappedComponentTree(ValidationResultModel, JComponent)},
     * not this constructor.<p>
     *
     * <strong>Note:</strong> You must not add or remove components
     * from the content once this constructor has been invoked.
     *
     * @param model      the ValidationResultModel to observe
     * @param content    the panel that contains the content components
     *
     * @throws NullPointerException if model or content is {@code null}.
     */
    public IconFeedbackPanel(ValidationResultModel model, JComponent content) {
        checkNotNull(model,  "The validation result model must not be null.");
        checkNotNull(content, "The content must not be null.");
        this.model   = model;
        this.content = content;
        setLayout(SimpleLayout.INSTANCE);
        add(content, CONTENT_LAYER);
        initEventHandling();
    }


    // Convenience Code *******************************************************

    /**
     * Wraps the components in the given component tree with instances
     * of IconFeedbackPanel where necessary. Such a wrapper is required
     * for all JScrollPanes that contain multiple children and
     * for the root - unless it's a JScrollPane with multiple children.
     *
     * @param model   reports changes in the contained ValidationResult
     * @param root    the root of the component tree to wrap
     * @return the wrapped component tree
     */
    public static JComponent getWrappedComponentTree(
            ValidationResultModel model,
            JComponent root) {
        wrapComponentTree(model, root);
        return isScrollPaneWithUnmarkableView(root)
            ? root
            : new IconFeedbackPanel(model, root);
    }

    private static void wrapComponentTree(
            ValidationResultModel model,
            Container container) {
        if (!(container instanceof JScrollPane)) {
            int componentCount = container.getComponentCount();
            for (int i = 0; i < componentCount; i++) {
                Component child = container.getComponent(i);
                if (child instanceof Container) {
                    wrapComponentTree(model, (Container) child);
                }
            }
            return;
        }
        JScrollPane scrollPane = (JScrollPane) container;
        JViewport viewport = scrollPane.getViewport();
        JComponent view = (JComponent) viewport.getView();
        if (isMarkable(view)) {
            return;
        }
        // TODO: Consider adding the following sanity check:
        // the view must not be an IconFeedbackPanel
        Component wrappedView = new IconFeedbackPanel(model, view);
        viewport.setView(wrappedView);
        wrapComponentTree(model, view);
    }

    private static boolean isScrollPaneWithUnmarkableView(Component c) {
        if (!(c instanceof JScrollPane)) {
            return false;
        }
        JScrollPane scrollPane = (JScrollPane) c;
        JViewport viewport = scrollPane.getViewport();
        JComponent view = (JComponent) viewport.getView();
        return !isMarkable(view);
    }


    // Initialization *********************************************************

    /**
     * Registers a listener with the validation result model that updates
     * the feedback components.
     */
    private void initEventHandling() {
        model.addPropertyChangeListener(
                ValidationResultModel.PROPERTY_RESULT,
                new ValidationResultChangeHandler());
    }


    // Abstract Behavior ******************************************************

    /**
     * Creates and returns a validation feedback component
     * that shall overlay the specified content component.<p>
     *
     * This implementation returns a JLabel. The validation result's severity
     * is used to lookup the label's icon; the result's message text is set
     * as the label's tooltip text.<p>
     *
     * TODO: Turn this method into an abstract method if this class
     * becomes an abstract superclass of general feedback overlay panels.
     *
     * @param result            determines the label's icon and tooltip text
     * @param contentComponent  the component to get overlaid feedback
     * @return the feedback component that overlays the content component
     *
     * @throws NullPointerException if the result is {@code null}
     */
    private static JComponent createFeedbackComponent(
            ValidationResult result,
            Component contentComponent) {
        Icon icon = ValidationResultViewFactory.getSmallIcon(result.getSeverity());
        JLabel label = new JLabel(icon);
        label.setToolTipText(getMessagesToolTipText(result));
        label.setSize(label.getPreferredSize());
        return label;
    }


    /**
     * Returns a string representation of the given validation result
     * that is intended for tool tips. It is invoked by
     * {@link #createFeedbackComponent(ValidationResult, Component)}
     * and the result won't be empty.
     *
     * @param result provides the ValidationMessages to iterate over
     * @return the empty string, if the result is empty;
     *     the formatted text of the first message, if the result has 1 message;
     *     an HTML string that has for each message a line with the
     *        message's formatted text.
     *
     * @since 1.3.1
     */
    private static String getMessagesToolTipText(ValidationResult result) {
        int size = result.size();
        if (size == 0) {
            return "";
        }
        if (size == 1) {
            return result.get(0).formattedText();
        }
        StringBuilder builder = new StringBuilder(128);
        builder.append("<html>")
               .append(result.get(0).formattedText());
        for (int i = 1; i < size; i++) {
            builder.append("<br>")
                   .append(result.get(i).formattedText());
        }
        builder.append("</html>");
        return builder.toString();
    }


    /**
     * Computes and returns the origin of the given feedback component
     * using the content component's origin.<p>
     *
     * This implementation returns a JLabel. The validation result's severity
     * is used to lookup the label's icon; the result's message text is
     * set as the label's tooltip text.<p>
     *
     * TODO: Turn this method into an abstract method if this class
     * becomes an abstract superclass of general feedback overlay panels.
     *
     * @param feedbackComponent  the component that overlays the content
     * @param contentComponent   the component to get overlaid feedback
     * @return the feedback component's origin
     *
     * @throws NullPointerException if the feedback component or content component
     *     is {@code null}
     */
    private static Point getFeedbackComponentOrigin(
            JComponent feedbackComponent,
            Component contentComponent) {
        boolean isLTR = contentComponent.getComponentOrientation().isLeftToRight();
        int x = contentComponent.getX()
              + (isLTR ? 0 : contentComponent.getWidth() - 1)
              - feedbackComponent.getWidth() / 2;
        int y = contentComponent.getY()
              + contentComponent.getHeight()
              - feedbackComponent.getHeight() + 2;

        return new Point(x, y);
    }


    // Updating the Overlay Components ****************************************

    private void removeAllFeedbackComponents() {
        synchronized (getTreeLock()) {
            int componentCount = getComponentCount();
            for (int i = componentCount - 1; i >= 0; i--) {
                Component child = getComponent(i);
                int layer = getLayer(child);
                if (layer == FEEDBACK_LAYER) {
                    remove(i);
                }
            }
        }
    }


    /**
     * Traverses the component tree starting at the given container
     * and creates a feedback component for each JTextComponent that
     * is associated with a message in the specified {@code keyMap}.<p>
     *
     * The arguments passed to the feedback component creation method
     * are the visited component and its associated validation sub result.
     * This sub result is requested from the specified {@code keyMap}
     * using the visited component's message key.
     *
     * @param container   the component tree root
     * @param keyMap      maps messages keys to associated validation results
     */
    private void visitComponentTree(Container container,
            Map<Object, ValidationResult> keyMap, int xOffset, int yOffset) {
        synchronized (container.getTreeLock()) {
            int componentCount = container.getComponentCount();
            for (int i = 0; i < componentCount; i++) {
                Component child = container.getComponent(i);
                if (!child.isVisible()) {
                    continue;
                }
                if (isMarkable(child)) {
                    if (isScrollPaneView(child)) {
                        Component containerParent = container.getParent();
                        addFeedbackComponent(
                                containerParent,
                                (JComponent) child,
                                keyMap,
                                xOffset - containerParent.getX(),
                                yOffset - containerParent.getY());
                    } else {
                        addFeedbackComponent(
                                child,
                                (JComponent) child,
                                keyMap,
                                xOffset,
                                yOffset);
                    }
                } else if (isScrollPaneView(child)) {
                    // Just do nothing.
                } else if (child instanceof Container) {
                    visitComponentTree(
                            (Container) child,
                            keyMap,
                            xOffset + child.getX(),
                            yOffset + child.getY());
                }
            }
        }
    }

    private static boolean isScrollPaneView(Component c) {
        Container container = c.getParent();
        Container containerParent = container.getParent();
        return container       instanceof JViewport
            && containerParent instanceof JScrollPane;
    }


    /**
     * Checks and answers if the given component can be marked or not.<p>
     *
     * TODO: Check the combobox editable state.<p>
     *
     * TODO: Add the JSpinner to the list of markable components.
     *
     * @param component  the component to be checked
     * @return true if the given component can be marked, false if not
     */
    private static boolean isMarkable(Component component) {
        return    component instanceof JTextComponent
               || component instanceof JComboBox;
    }

    private void addFeedbackComponent(
            Component contentComponent,
            JComponent messageComponent,
            Map<Object, ValidationResult> keyMap, int xOffset, int yOffset) {
        ValidationResult result = getAssociatedResult(messageComponent, keyMap);
        if (result.isEmpty()) {
            return;
        }
        JComponent feedbackComponent = createFeedbackComponent(result, contentComponent);
        add(feedbackComponent, Integer.valueOf(FEEDBACK_LAYER));
        Point overlayPosition = getFeedbackComponentOrigin(feedbackComponent, contentComponent);
        overlayPosition.translate(xOffset, yOffset);
        feedbackComponent.setLocation(overlayPosition);
    }


    /**
     * Returns the ValidationResult associated with the given component
     * using the specified validation result key map. Unlike
     * {@link ValidationComponentUtils#getAssociatedResult(JComponent, Map)}
     * this method returns the empty result if the component has no keys set.
     *
     * @param comp     the component may be marked with a validation message key
     * @param keyMap   maps validation message keys to ValidationResults
     * @return the ValidationResult associated with the given component
     *     as provided by the specified validation key map
     *     or {@code ValidationResult.EMPTY} if the component has no message key set,
     *     or {@code ValidationResult.EMPTY} if no result is associated
     *     with the component
     */
    private static ValidationResult getAssociatedResult(
            JComponent comp, Map<Object, ValidationResult> keyMap) {
        ValidationResult result = ValidationComponentUtils.getAssociatedResult(comp, keyMap);
        return result == null
            ? ValidationResult.EMPTY
            : result;
    }


    // Event Handling *********************************************************

    private void updateFeedbackComponents() {
        removeAllFeedbackComponents();
        revalidate();
        visitComponentTree(content, model.getResult().keyMap(), 0, 0);
        repaint();
    }

    /**
     * Ensures that the feedback components are repositioned.
     * Invoked by {@code #validate}, i. e. if this panel is laid out.<p>
     *
     * TODO: Improve this implementation to set only positions.
     * The current implementation removes all components and re-adds
     * them later.
     */
    private void repositionFeedbackComponents() {
        updateFeedbackComponents();
    }


    /**
     * Recursively descends the container tree and recomputes the
     * layout for any subtrees marked as needing it (those marked as
     * invalid). In addition to the superclass behavior, we reposition
     * the feedback components after the child components have been
     * validated.<p>
     *
     * We reposition the feedback components only, if this panel is visible;
     * if it becomes visible, #validateTree will be invoked.
     *
     * @see Container#validateTree()
     * @see #validate()
     * @see #invalidate()
     * @see #doLayout()
     * @see Component#setVisible(boolean)
     * @see LayoutManager
     */
    @Override
    protected void validateTree() {
        super.validateTree();
        if (isVisible()) {
            repositionFeedbackComponents();
        }
        // Validate again, since the removal of the feedback components
        // may have invalidated this component.
        super.validateTree();
    }


    /**
     * Gets notified when the ValidationResult changed.
     * Updates the feedback components.
     */
    private final class ValidationResultChangeHandler implements PropertyChangeListener {

        @Override
		public void propertyChange(PropertyChangeEvent evt) {
            updateFeedbackComponents();
        }

    }


    // Layout *****************************************************************

    /**
     * Used to lay out the content layer in the icon feedback JLayeredPane.
     * The content fills the parent's space; minimum and preferred size of
     * this layout are requested from the content panel.
     */
    private final static class SimpleLayout implements LayoutManager {

        static final LayoutManager INSTANCE = new SimpleLayout();

        /**
         * If the layout manager uses a per-component string,
         * adds the component {@code comp} to the layout,
         * associating it
         * with the string specified by {@code name}.
         *
         * @param name the string to be associated with the component
         * @param comp the component to be added
         */
        @Override
		public void addLayoutComponent(String name, Component comp) {
            // components are well known by the container
        }

        /**
         * Removes the specified component from the layout.
         * @param comp the component to be removed
         */
        @Override
		public void removeLayoutComponent(Component comp) {
            // components are well known by the container
        }

        /**
         * Calculates the preferred size dimensions for the specified
         * container, given the components it contains.
         *
         * @param parent the container to be laid out
         * @return the preferred size of the given container
         * @see #minimumLayoutSize(Container)
         */
        @Override
		public Dimension preferredLayoutSize(Container parent) {
            return content(parent).getPreferredSize();
        }

        /**
         * Calculates the minimum size dimensions for the specified
         * container, given the components it contains.
         *
         * @param parent the component to be laid out
         * @return the minimum size of the given container
         * @see #preferredLayoutSize(Container)
         */
        @Override
		public Dimension minimumLayoutSize(Container parent) {
            return content(parent).getMinimumSize();
        }

        /**
         * Lays out the specified container.
         *
         * @param parent the container to be laid out
         */
        @Override
		public void layoutContainer(Container parent) {
            Dimension size = parent.getSize();
            content(parent).setBounds(0, 0, size.width, size.height);
        }

        private static JComponent content(Container parent) {
            return ((IconFeedbackPanel) parent).content;
        }

    }


}
