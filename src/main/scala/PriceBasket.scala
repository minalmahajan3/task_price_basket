object PriceBasket {
  // Define the prices for each item
  val itemPrices: Map[String, Double] = Map(
    "Apples" -> 1.00,
    "Soup" -> 0.65,
    "Bread" -> 0.80,
    "Milk" -> 1.30
  )


  // Define special offers for certain items
  val specialOffers: Map[String, (Double, String)] = Map(
    "Apples" -> (0.10, "Apples 10% off"),
    "Soup_Bread" -> (0.50, "Buy 2 tins of Soup, get Bread for half price")
  )

  def main(args: Array[String]): Unit = {
    // Check if any items are provided as command-line arguments
    if (args.isEmpty) {
      println("Basket is Empty, Provide an input")
//      println(unavailableItems)
    }
    else {
      // Convert command-line arguments to a list of items
      val basket = args.toList
      val unavailableItems = basket.filterNot(itemPrices.contains)
      if (unavailableItems.isEmpty) {

        // Calculate the total price and applicable discounts
        val (subtotal, total, discounts) = calculateBasketTotal(basket)
        // Print the receipt with the subtotal, discounts, and final total
        printReceipt(subtotal, total, discounts)
      } else {
        // Print items that are not available
        println(s"The following items are not available: ${unavailableItems.mkString(", ")}")
      }

    }
  }

  def calculateBasketTotal(basket: List[String]): (Double,Double, List[(String, Double)]) = {
    // Count the occurrences of each item in the basket
    val itemCounts: Map[String, Int] = basket.groupBy(identity).view.mapValues(_.size).toMap
    // Count the number of Soup and Bread items separately
    val soupCount = itemCounts.getOrElse("Soup", 0)
    val breadCount = itemCounts.getOrElse("Bread", 0)
    // Calculate the subtotal, discounts, and total
    val subtotal = calculateSubtotal(basket)
    val discounts = calculateDiscounts(itemCounts, soupCount, breadCount)
    val total = subtotal - discounts.map(_._2).sum
    (subtotal,total, discounts)
  }

  def calculateSubtotal(basket: List[String]): Double = {
    // Calculate the subtotal by summing up the prices of items in the basket
    basket.map(item => itemPrices.getOrElse(item, 0.0)).sum
  }

  def calculateDiscounts(itemCounts: Map[String, Int], soupCount: Int, breadCount: Int): List[(String, Double)] = {
    itemCounts.toList.flatMap {
      // Check if the item is "Soup" and apply the Soup and Bread discount if conditions are met
      case ("Soup", _) =>
        itemCounts.get("Bread") match {
          case Some(_) if soupCount >= 2 && breadCount >= 1 =>
            val maxDiscount = Math.min(soupCount / 2, breadCount) * itemPrices.getOrElse("Bread", 0.0) * specialOffers("Soup_Bread")._1
            List(("Buy 2 tins of Soup, get Bread for half price", maxDiscount))
          case _ => Nil
        }
      // Check if the item is "Apples" and apply the Apple discount
      case ("Apples", applesCount) if applesCount >= 1 =>
        val appleDiscount = applesCount * itemPrices.getOrElse("Apples", 0.0) * specialOffers("Apples")._1
        List(("Apples 10% off", appleDiscount))
      // For other items, no discounts are applicable
      case _ => Nil
    }
  }

  def printReceipt(subtotal:Double,total: Double, discounts: List[(String, Double)]): Unit = {
    // Calculate the subtotal including discounts
    val subtotal = total + discounts.map(_._2).sum
    // Print the receipt with subtotal, discounts, and total
    println(s"Subtotal: £${"%.2f".format(subtotal)}")
    if (discounts.isEmpty) {
      println("(No offers available)")
    }

    else {
      discounts.foreach {
        case (offerDesc, discount) =>
          println(s"$offerDesc: £${"%.2f".format(discount)}")
      }
    }
    println(s"Total price: £${"%.2f".format(total)}")
  }
}
