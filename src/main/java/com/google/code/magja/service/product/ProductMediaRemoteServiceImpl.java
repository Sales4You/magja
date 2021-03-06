package com.google.code.magja.service.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.magja.magento.ResourcePath;
import com.google.code.magja.model.product.Product;
import com.google.code.magja.model.product.ProductMedia;
import com.google.code.magja.service.GeneralServiceImpl;
import com.google.code.magja.service.ServiceException;
import com.google.code.magja.soap.SoapClient;

/**
 * Product media service implementation.
 * @author andre
 * @author Simon Zambrovski
 */
public class ProductMediaRemoteServiceImpl extends GeneralServiceImpl<ProductMedia> implements ProductMediaRemoteService {

  private transient Logger log = LoggerFactory.getLogger(ProductMediaRemoteServiceImpl.class);
  private static final long serialVersionUID = -1848723516561700531L;

  public ProductMediaRemoteServiceImpl(SoapClient soapClient) {
    super(soapClient);
  }

  /**
   * Build the object ProductMedia with the Map returned by the api
   *
   * @param map
   * @return ProductMedia
   */
  private ProductMedia buildProductMedia(Map<String, Object> map) {
    ProductMedia prd_media = new ProductMedia();

    for (Map.Entry<String, Object> att : map.entrySet())
      prd_media.set(att.getKey(), att.getValue());

    if (map.get("types") != null) {
      prd_media.setTypes(new HashSet<ProductMedia.Type>());
      List<String> types = (List<String>) map.get("types");
      for (String type : types)
        prd_media.getTypes().add(ProductMedia.Type.getValueOfString(type));
    }

    return prd_media;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * com.google.code.magja.service.product.ProductMediaRemoteService#delete(code
   * .google.magja.model.product.ProductMedia)
   */
  @Override
  public void delete(ProductMedia productMedia) throws ServiceException {
    // List<Object> params = new LinkedList<Object>();
    // params.add((productMedia.getProduct().getId() != null ? productMedia
    // .getProduct().getId() : productMedia.getProduct().getSku()));
    // params.add(productMedia.getFile());

    Boolean success = false;
    try {
      success = (Boolean) soapClient.callArgs(ResourcePath.ProductAttributeMediaRemove, new Object[] { productMedia.getFile() });
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

    if (!success)
      throw new ServiceException("Error deleting the Product Media");
  }

  /*
   * (non-Javadoc)
   *
   * @seecom.google.code.magja.service.product.ProductMediaRemoteService#
   * getByProductAndFile(com.google.code.magja.model.product.Product,
   * java.lang.String)
   */
  @Override
  public ProductMedia getByProductAndFile(Product product, String file) throws ServiceException {

    if (!ProductServiceUtil.validateProduct(product))
      throw new ServiceException("the product for the media must be setted.");

    // List<Object> params = new LinkedList<Object>();
    // params.add((product.getId() != null ? product.getId() : product
    // .getSku()));
    // params.add(file);

    Map<String, Object> media = null;
    try {
      media = soapClient.callArgs(ResourcePath.ProductAttributeMediaInfo, new Object[] { product.getId() != null ? product.getId() : product.getSku(), file });
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

    return buildProductMedia(media);
  }

  /*
   * (non-Javadoc)
   *
   * @seecom.google.code.magja.service.product.ProductMediaRemoteService#
   * getMd5(java.lang.String)
   */
  @Override
  public String getMd5(String file) throws ServiceException {
    // List<Object> params = new LinkedList<Object>();
    // params.add(file);

    String media = null;
    try {
      media = (String) soapClient.callArgs(ResourcePath.ProductAttributeMediaMd5, new Object[] { file });
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

    return media;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.google.code.magja.service.product.ProductMediaRemoteService#
   * listByProduct (com.google.code.magja.model.product.Product)
   */
  @Override
  public List<ProductMedia> listByProduct(Product product) throws ServiceException {

    if (!ProductServiceUtil.validateProduct(product))
      throw new ServiceException("The product must have the id or the sku seted for list medias");

    List<ProductMedia> result = new ArrayList<ProductMedia>();

    List<Map<String, Object>> medias = null;
    try {
      medias = soapClient.callSingle(ResourcePath.ProductAttributeMediaList, (product.getId() != null ? product.getId() : product.getSku()));
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

    if (medias == null)
      return null;

    for (Map<String, Object> media : medias) {
      ProductMedia productMedia = buildProductMedia(media);
      result.add(productMedia);
    }

    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * com.google.code.magja.service.product.ProductMediaRemoteService#create(code
   * .google.magja.model.product.ProductMedia)
   */
  @Override
  public void create(ProductMedia productMedia) throws ServiceException {
     if (productMedia.getImage() == null)
      throw new ServiceException("the image is null.");

    if (productMedia.getImage().getData() == null)
      throw new ServiceException("invalid binary data for the image.");

    try {
      String result = (String) soapClient.callArgs(ResourcePath.ProductAttributeMediaCreate, productMedia.serializeToApi());

      productMedia.setFile(result);

    } catch (AxisFault e) {
      log.error("Cannot create ProductMedia " + productMedia.getLabel(), e);
      if (debug)
        e.printStackTrace();
      throw new ServiceException("Cannot create ProductMedia " + productMedia.getLabel(), e);
    }
  }

  @Override
  public Boolean update(ProductMedia productMedia) throws ServiceException {
    Map<String, Object> props = productMedia.getAllProperties();
    String[] str_types = new String[productMedia.getTypes().size()];
    int i = 0;
    for (ProductMedia.Type type : productMedia.getTypes())
      str_types[i++] = type.toString().toLowerCase();

    if (str_types.length > 0) {
      props.put("types", str_types);
    } else {
      props.put("types", "");
    }

    props.put("file", productMedia.getImage().serializeToApi());

    // List<Object> newMedia = new LinkedList<Object>();
    // newMedia.add(productMedia.getProduct().getSku());
    // newMedia.add(productMedia.getFile());
    //
    // props.remove("url");
    //
    // newMedia.add(props);

    try {
      Boolean result = (Boolean) soapClient.callArgs(ResourcePath.ProductAttributeMediaUpdate,
          new Object[] { productMedia.getFile(), props.remove("url") });
      return result;
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

  }

}
