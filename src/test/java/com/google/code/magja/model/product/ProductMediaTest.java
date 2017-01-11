/**
 *
 */
package com.google.code.magja.model.product;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.magja.utils.MagjaFileUtils;

/**
 * Tests the FileUtils.
 * 
 * @author andre
 */
public class ProductMediaTest {

  private String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/"
      + "Magento_2_Admin_Panel_screenshot.png/1280px-Magento_2_Admin_Panel_screenshot.png";
  private final static Logger log = LoggerFactory.getLogger(ProductMediaTest.class);

  @Test
  public void testImageFileIThree() throws Exception {
    byte[] data = MagjaFileUtils.getBytesFromFileURL(imageUrl);
    assertNotNull(data);
  }

  @Test
  public void testImageFileTwo() throws Exception {
    BufferedImage image = ImageIO.read(new URL(imageUrl));
    log.info("{}", image);
    assertTrue(image.getHeight() != 0);
    assertTrue(image.getWidth() != 0);
    byte[] data = MagjaFileUtils.getBytesFromBufferedImage(image, "PNG");
    assertNotNull(data);
  }

}
