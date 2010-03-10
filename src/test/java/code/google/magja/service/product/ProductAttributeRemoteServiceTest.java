/**
 *
 */
package code.google.magja.service.product;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import code.google.magja.model.product.ProductAttribute;
import code.google.magja.model.product.ProductAttributeSet;
import code.google.magja.model.product.ProductType;
import code.google.magja.model.product.ProductTypeEnum;
import code.google.magja.service.RemoteServiceFactory;
import code.google.magja.service.ServiceException;
import code.google.magja.utils.MagjaStringUtils;

/**
 * @author andre
 *
 */
public class ProductAttributeRemoteServiceTest {

	private ProductAttributeRemoteService service;

	private ProductAttributeSet defaultAttributeSet;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		service = RemoteServiceFactory.getProductAttributeRemoteService();
		defaultAttributeSet = ProductAttributeSet.getDefaultProductAttributeSet();
	}

	/**
	 * Test method for {@link code.google.magja.service.product.ProductAttributeRemoteServiceImpl#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {
		try {

			// first, create a attribute to be deleted
			ProductAttribute pa = createTextAttributeSimple();
			service.save(pa);
			assertTrue(pa.getId() != null);

			// then, delete the pa
			service.delete(pa.getCode());

		} catch (ServiceException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link code.google.magja.service.product.ProductAttributeRemoteServiceImpl#getOptions(code.google.magja.model.product.ProductAttribute)}.
	 */
	@Test
	public void testGetOptions() {

		try {

			ProductAttribute pa = new ProductAttribute();
			pa.setCode("color");
			service.getOptions(pa);
			for (Map.Entry<Integer, String> opt : pa.getOptions().entrySet())
				System.out.println(opt.toString());

		} catch (ServiceException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link code.google.magja.service.product.ProductAttributeRemoteServiceImpl#listAllProductAttributeSet()}.
	 */
	@Test
	public void testListAllProductAttributeSet() {
		try {
			List<ProductAttributeSet> sets = service.listAllProductAttributeSet();
			for (ProductAttributeSet set : sets) System.out.println(set.toString());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link code.google.magja.service.product.ProductAttributeRemoteServiceImpl#listByAttributeSet(code.google.magja.model.product.ProductAttributeSet)}.
	 */
	@Test
	public void testListByAttributeSet() {
		try {
			List<ProductAttribute> results = service.listByAttributeSet(defaultAttributeSet);
			for (ProductAttribute productAttribute : results)
				System.out.println(productAttribute.toString());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link code.google.magja.service.product.ProductAttributeRemoteServiceImpl#save(code.google.magja.model.product.ProductAttribute)}.
	 */
	@Test
	public void testSave() {

		try {
			ProductAttribute pa1 = createTextAttributeSimple();
			service.save(pa1);

			assertTrue(pa1.getId() != null);

			ProductAttribute pa2 = createSelectWithOptionsAndApplyToSpecified();
			service.save(pa2);

			assertTrue(pa2.getId() != null);

			// comment if you don't want to delete the created pa's
			service.delete(pa1.getCode()); service.delete(pa2.getCode());

		} catch (ServiceException e) {
			fail(e.getMessage());
		}

	}

	private ProductAttribute createTextAttributeSimple() {

		ProductAttribute attribute = new ProductAttribute();

		attribute.setCode(MagjaStringUtils.randomString(3, 8).toUpperCase());
		attribute.setScope(ProductAttribute.Scope.STORE);
		attribute.setGroup("General");
		attribute.setType("varchar");
		attribute.setBackend("");
		attribute.setFrontend("");
		attribute.setLabel(MagjaStringUtils.randomString(3, 8).toUpperCase());
		attribute.setInput("text");
		attribute.setAttributeClass("");
		attribute.setSource("");
		attribute.setVisible(true);
		attribute.setRequired(false);
		attribute.setUserDefined(true);
		attribute.setDefaultValue("");
		attribute.setSearchable(true);
		attribute.setFilterable(true);
		attribute.setComparable(true);
		attribute.setVisibleOnFront(true);
		attribute.setVisibleInAdvancedSearch(true);
		attribute.setUnique(false);

		return attribute;
	}

	public ProductAttribute createSelectWithOptionsAndApplyToSpecified() {

		ProductAttribute attribute = new ProductAttribute();

		attribute.setCode(MagjaStringUtils.randomString(3, 8).toUpperCase());
		attribute.setScope(ProductAttribute.Scope.STORE);

		attribute.setGroup("General");
		attribute.setType("int");
		attribute.setBackend("");
		attribute.setFrontend("");
		attribute.setLabel(MagjaStringUtils.randomString(3, 8).toUpperCase());
		attribute.setInput("select");
		attribute.setAttributeClass("");
		attribute.setSource("");
		attribute.setVisible(true);
		attribute.setRequired(false);
		attribute.setUserDefined(true);
		attribute.setDefaultValue("");
		attribute.setSearchable(true);
		attribute.setFilterable(true);
		attribute.setComparable(true);
		attribute.setVisibleOnFront(false);
		attribute.setVisibleInAdvancedSearch(true);
		attribute.setUnique(false);

		attribute.setApplyTo(new ArrayList<ProductType>());
		attribute.getApplyTo().add(ProductTypeEnum.SIMPLE.getProductType());
		attribute.getApplyTo().add(ProductTypeEnum.GROUPED.getProductType());
		attribute.getApplyTo().add(ProductTypeEnum.CONFIGURABLE.getProductType());

		// create some options
		attribute.setOptions(new HashMap<Integer, String>());
		attribute.getOptions().put(1, MagjaStringUtils.randomString(3, 8));
		attribute.getOptions().put(2, MagjaStringUtils.randomString(3, 8));
		attribute.getOptions().put(3, MagjaStringUtils.randomString(3, 8));

		return attribute;
	}

}
