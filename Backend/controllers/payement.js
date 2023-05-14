// This is a public sample test API key.
// Don’t submit any personally identifiable information in requests made with this key.

import Stripe from "stripe"
// Sign in to see your own test API key embedded in code samples.
const stripe = new Stripe (
  "sk_test_51N1JhpF3oMm4gZ9sJayEpLtPzEj99XyymkJOBbWiXwizpC4z2Ft9PtNnu2MYzFNzNfctwqfcHXiPyMzcK2P1Z36b00kvO6WZVd"
);

export const payement = async (req, res) => {
  // Use an existing Customer ID if this is a returning customer.

 // const newreservation = new reservation(req.body);

  //var payement = await newreservation.findById(req.params.id);
try{
  const customer = await stripe.customers.create();
  console.log(customer)

  const ephemeralKey = await stripe.ephemeralKeys.create(
    { customer: customer.id },
    { apiVersion: "2022-11-15" }
  );
  var amount_number = Math.round(req.params.amount*100) 
  const paymentIntent = await stripe.paymentIntents.create({
    amount:amount_number,
    currency: "usd",
    customer: customer.id,
 payment_method_types: ['card'],
  });
  console.log(paymentIntent)

  res.json({
    paymentIntent: paymentIntent.client_secret,
    ephemeralKey: ephemeralKey.secret,
    customer: customer.id,
    publishableKey:
      "pk_test_51N1JhpF3oMm4gZ9s80g0o6utbKtZuN7hEa5oF8x72c7LxeJM8B3dMiqgMR1QT4huoUfazVFcsPlYUtZtnWTaRIVo004uC1BERl",
  });
  console.log(res)
}catch(err)
{
  switch (err.type) {
    case 'StripeCardError':
      console.log(`A payment error occurred: ${err.message}`);
      break;
    case 'StripeInvalidRequestError':
      console.log(`An invalid request occurred. ${err.message}`);

      break;
    default:
      console.log(`Another problem occurred, maybe unrelated to Stripe. ${err.message}`);

      break;
  }
}};