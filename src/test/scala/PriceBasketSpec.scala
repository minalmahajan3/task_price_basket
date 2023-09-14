import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PriceBasketSpec extends AnyFlatSpec with Matchers {

  // Helper function to round a double to a specific number of decimal places
  def round(value: Double, places: Int): Double = {
    val scale = math.pow(10, places)
    (value * scale).round / scale
  }

  // Test case to check if the total is calculated correctly for a basket without discounts
  it should "calculate the total for a basket without discounts" in {
    val basket = List("Soup", "Bread", "Milk")
    val (subtotal, total, discounts) = PriceBasket.calculateBasketTotal(basket)

    total shouldEqual 2.75
    discounts shouldBe empty
  }

  // Test case to check if the Apple discount is applied correctly
  it should "apply the Apple discount correctly" in {
    val basket = List("Apples", "Milk", "Apples")
    val (subtotal, total, discounts) = PriceBasket.calculateBasketTotal(basket)

    round(total, 2) shouldEqual round(3.10, 2) // Round to 2 decimal places
    discounts should contain only ("Apples 10% off" -> round(0.20, 2)) // Round to 2 decimal places
  }

  // Test case to check if the Soup and Bread discount is applied correctly
  it should "apply the Soup and Bread discount correctly" in {
    val basket = List("Soup", "Soup", "Bread")
    val (subtotal, total, discounts) = PriceBasket.calculateBasketTotal(basket)

    round(total, 2) shouldEqual round(1.70, 2) // Round to 2 decimal places
    discounts should contain only ("Buy 2 tins of Soup, get Bread for half price" -> round(0.40, 2)) // Round to 2 decimal places
  }

  // Test case to check if the total and discounts are calculated accurately
  it should "calculate the total and discounts accurately" in {
    val basket = List("Apples", "Apples", "Soup", "Soup", "Bread", "Milk")
    val (subtotal, total, discounts) = PriceBasket.calculateBasketTotal(basket)

    round(total, 2) shouldEqual round(4.80, 2) // Round to 2 decimal places
    discounts should contain only ("Buy 2 tins of Soup, get Bread for half price" -> round(0.40, 2), ("Apples 10% off", 0.20)) // Round to 2 decimal places
  }

  // Test case to check if the code handles an empty basket correctly
  it should "handle an empty basket" in {
    val basket = List.empty[String]
    val (subtotal, total, discounts) = PriceBasket.calculateBasketTotal(basket)

    total shouldEqual 0.0
    discounts shouldBe empty
  }

  // Test case to check if the receipt is printed correctly with discounts
  it should "print the receipt correctly with discounts" in {
    val subtotal = 3.10
    val total = 2.60
    val discounts = List("Apples 10% off" -> 0.10, "Buy 2 tins of Soup, get Bread for half price" -> 0.40)

    val expectedOutput =
      """Subtotal: £3.10
        |Apples 10% off: £0.10
        |Buy 2 tins of Soup, get Bread for half price: £0.40
        |Total price: £2.60""".stripMargin

    val printedOutput = captureOutput(PriceBasket.printReceipt(subtotal, total, discounts))

    printedOutput shouldEqual expectedOutput
  }

  // Test case to check if the receipt is printed correctly with no discounts
  it should "print the receipt correctly with no discounts" in {
    val subtotal = 1.30
    val total = 1.30
    val discounts = List.empty[(String, Double)]

    val expectedOutput =
      """Subtotal: £1.30
        |(No offers available)
        |Total price: £1.30""".stripMargin

    val printedOutput = captureOutput(PriceBasket.printReceipt(subtotal, total, discounts))

    printedOutput shouldEqual expectedOutput
  }
  //Test case to check if item is available in basket or not
  it should "handle items not in the basket" in {
    val basket=List("Eggs","Bananas")
    val (subtotal, total, discounts) = PriceBasket.calculateBasketTotal(basket)
    //printedOutput shouldEqual expectedOutput

    val expectedOutput =
   """
    subtotal shouldEqual 0.0
    total shouldEqual 0.0
    discounts shouldBe empty """.stripMargin
    val printedOutput = captureOutput(PriceBasket.printReceipt(subtotal, total, discounts))
  }

  // Helper function to capture output for testing
  private def captureOutput(block: => Unit): String = {
    val outContent = new java.io.ByteArrayOutputStream()
    Console.withOut(outContent)(block)
    outContent.toString.trim
  }
}
