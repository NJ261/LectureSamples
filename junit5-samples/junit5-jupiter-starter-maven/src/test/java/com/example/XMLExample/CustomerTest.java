package com.example.XMLExample;

//import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.fail;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class CustomerTest
{
	// This is the mock ICreditCardProcessor object.
	static ICreditCardProcessor cardProcessor;

	@BeforeAll
	static void initAll()
	{
		ArrayList<String> validCreditCards = new ArrayList<String>();
		validCreditCards.add("4138389928849938");
		validCreditCards.add("1234567890123456");
		validCreditCards.add("0987654321098765");
		cardProcessor = new CreditCardProcessorMock(validCreditCards);
	}

	@Test
	@DisplayName("CustomerTest - Testing serializing the Customer object")
	void customerSerializationTest()
	{
		Customer customer = new Customer();
		customer.setEmail("rhawkey@dal.ca");
		customer.setCreditCard("4138389928849938");

		/*	The following code would output the following XML to customer.xml:
			
			<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
			<customer>
    			<creditCard>4138389928849938</creditCard>
    			<email>rhawkey@dal.ca</email>
			</customer>

			Remember though that we don't want to rely on data layers in our testing,
			because of this, and because we know the exact inputs above, we know
			what the output XML should be, and can put that here rather than getting files
			involved.
		*/
		/*
		try
		{
			File file = new File("customer.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(customer, file);
			jaxbMarshaller.marshal(customer, System.out);

	     }
	     catch (JAXBException e)
	     {
			e.printStackTrace();
	     }
	    */
	    // Here's the check just by marshalling into a stringwriter.
	    try
	    {
	    	java.io.StringWriter sw = new StringWriter();
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    	jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    	jaxbMarshaller.marshal(customer, sw);
	    	// sw now has the XML that would have been in that file.  Since we don't care about
	    	// capitals in XML, and we know the data we entered is all lowercase, we can convert
	    	// the whole thing to lowercase, and verify that the string matches the output we expect.
			final String EXPECTED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
										"<customer>\n" +
										"    <creditCard>4138389928849938</creditCard>\n" +
										"    <email>rhawkey@dal.ca</email>\n" +
										"</customer>\n";
			assertEquals(EXPECTED_XML.toLowerCase(), sw.toString().toLowerCase());
	    }
	    catch (JAXBException e)
	    {
	    	// This is one way to detect and handle exceptions in unit tests, however you can
	    	// also write your test to intentially CAUSE an exception and check that the
	    	// exception happens.  Read this for more info: 
	    	// https://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-4-tests
	    	fail("Failed to output Customer object to XML");
	    }
	}

	/*
		The following are tests that make use of the CreditCardProcessorMock mock object.  This is similar to what you'll do
		with the Security interface in the assignment.
	*/
	@Test
	@DisplayName("InvalidCreditCardTest")
	void invalidCreditCardTest()
	{
		Customer customer = new Customer();
		customer.setEmail("rhawkey@dal.ca");
		customer.setCreditCard("4");
		try
		{
			assertEquals(Customer.CreditCardResults.INVALID_CREDIT_CARD, customer.ChargeCreditCard(200.0f, cardProcessor));
		}
		catch (Exception e)
		{
			fail("Failed calling ChargeCreditCard() in InvalidCreditCardTest");
		}
	}

	@Test
	@DisplayName("CreditCardChargeFailureTest")
	void creditCardChargeFailureTest()
	{
		try
		{
			Customer customer = new Customer();
			customer.setEmail("rhawkey@dal.ca");
			customer.setCreditCard("4138389928849938");
			assertEquals(Customer.CreditCardResults.CREDIT_CARD_CHARGE_FAILURE, customer.ChargeCreditCard(1.0f, cardProcessor));
		}
		catch (Exception e)
		{
			fail("Failed calling ChargeCreditCard() in CreditCardChargeFailureTest");
		}
	}

	@Test
	@DisplayName("CreditCardChargeSuccessTest")
	void creditCardChargeSuccessTest()
	{
		try
		{
			Customer customer = new Customer();
			customer.setEmail("rhawkey@dal.ca");
			customer.setCreditCard("4138389928849938");
			assertEquals(Customer.CreditCardResults.SUCCESS, customer.ChargeCreditCard(200.0f, cardProcessor));
		}
		catch (Exception e)
		{
			fail("Failed calling ChargeCreditCard() in CreditCardChargeSuccessTest");
		}
	}
}












