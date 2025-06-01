# Firestore Data Schema Draft

## Collection: users
- **uid**: string (user ID)
- **name**: string
- **email**: string
- **createdAt**: timestamp

## Collection: products
- **productId**: string
- **name**: string
- **price**: number
- **imageUrl**: string
- **stock**: number

## Collection: carts
- **userId**: string (reference to users)
- **items**: array of {
  productId: string  
  quantity: number  
  price: number *(price per unit at time of adding to cart)*  
  total: number *(price * quantity)*
  }
- **deliveryOption**: string *(either "pickup" or "delivery")*
- **deliveryFee**: number *(0 if pickup, a set fee if delivery)*
- **cartTotal**: number *(sum of item totals + deliveryFee)*
                                                             