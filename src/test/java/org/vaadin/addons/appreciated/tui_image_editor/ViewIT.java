package org.vaadin.addons.appreciated.tui_image_editor;

import com.vaadin.testbench.TestBenchElement;
import org.junit.Assert;
import org.junit.Test;

public class ViewIT extends AbstractViewTest {

    @Test
    public void componentWorks() {
        final TestBenchElement paperSlider = $("editor").first();
        // Check that editor contains at least one other element, which means that
        // is has been upgraded to a custom element and not just rendered as an empty
        // tag
        Assert.assertTrue(
                paperSlider.$(TestBenchElement.class).all().size() > 0);
    }
}
