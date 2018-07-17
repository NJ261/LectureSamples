package com.example.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StoreTest
{
	private static IPromotionDatabase promotionDatabase;
	private static IProductDatabase productDatabase;
	private static final float ALLOWED_FLOAT_DELTA = 0.00001f;
	
	@BeforeAll
   public static void SetUp()
	{
		productDatabase = new ProductDatabaseMock();
		promotionDatabase = new PromotionDatabaseMock();
	}
	
	@Test
	@DisplayName("CalculateValueAllStock")
	void CalculateValueAllStock()
	{
		Store store = new Store(productDatabase, promotionDatabase, "", "", "", "");
		assertEquals(5.46f, store.CalculateValueAllStock(), ALLOWED_FLOAT_DELTA);
	}
	
	@Test
	@DisplayName("CalculateMarketValueAllStock")
	void CalculateMarketValueAllStock()
	{
		Store store = new Store(productDatabase, promotionDatabase, "", "", "", "");
		assertEquals(9.6564f, store.CalculateMarketValueAllStock(), ALLOWED_FLOAT_DELTA);
	}
	
	@Test
	@DisplayName("GetContactInfo")
	void GetContactInfo()
	{
		Store store = new Store(productDatabase, promotionDatabase, "1 Street", "Halifax", "Nova Scotia", "B2T1A4");
		assertEquals("1 Street\nHalifax\nNova Scotia\nB2T1A4", store.GetContactInfo());
	}
}