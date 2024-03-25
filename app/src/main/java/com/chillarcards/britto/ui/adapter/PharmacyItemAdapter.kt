import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.DummyItem
import com.chillarcards.britto.ui.DummyOrderItems
import com.chillarcards.britto.ui.interfaces.OnCallBackListner
import com.chillarcards.britto.ui.interfaces.OnIncrementListener
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.NumberCounterView
class PharmacyItemAdapter(
    private val items: List<DummyItem>,
    private val context: Context?,
    private val getCartUtil: OnCallBackListner,
) : RecyclerView.Adapter<PharmacyItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_pharmacy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.tvMainName)
        private val itemBrand: TextView = itemView.findViewById(R.id.tvDistri)
        private val itemOffer: TextView = itemView.findViewById(R.id.tvOffers)
        private val itemRate: TextView = itemView.findViewById(R.id.tvRate)
        val addCart: TextView = itemView.findViewById(R.id.add_to_cart)
        val itemQty: NumberCounterView = itemView.findViewById(R.id.QtyEBTN)

        fun bind(item: DummyItem) {
            itemName.text = item.prdname
            itemBrand.text = item.prdbrand

            val correspondingCartItem = Const.cartItems.find { it.id == item.id }

            if (correspondingCartItem != null) {
                // Item exists in the cart
                addCart.visibility = View.GONE
                itemQty.visibility = View.VISIBLE
                // Set the count of NumberCounterView to the quantity in the cart
                itemQty.setCount(correspondingCartItem.cartQty.toInt())
            } else {
                // Item does not exist in the cart
                addCart.visibility = View.VISIBLE
                itemQty.visibility = View.GONE
            }

            // Handle the visibility of offer TextView and set its text
            handleOfferTextView(item)

            // Set click listener for adding to cart
            addCart.setOnClickListener {
                addCart.visibility = View.GONE
                itemQty.visibility = View.VISIBLE
                // Update the cart
                addToCart(item, 1)
            }

            // Set increment listener for item quantity
            itemQty.setIncrementListener(object : OnIncrementListener {
                override fun onIncrement(count: Int) {
                    // Update the cart when quantity is incremented
                    addToCart(item, count)
                    getCartUtil.onAddtocartCallback()
                    notifyDataSetChanged()
                }

                override fun onDecrement(count: Int) {
                    // Decrement quantity or remove from cart if count is 0
                    addToCart(item, count)
                    getCartUtil.onAddtocartCallback()
                    notifyDataSetChanged()
                }
            })
        }

        private fun addToCart(item: DummyItem, quantity: Int) {
            val index = Const.cartItems.indexOfFirst { it.id == item.id }
            if (index != -1) {
                if (quantity > 0) {
                    // Update existing item quantity in the cart
                    Const.cartItems[index].cartQty = quantity.toString()
                    // Update sell rate based on the updated quantity
                    Const.cartItems[index].cartRate = (item.prdsellrate.toFloat() * quantity).toString()
                } else {
                    // Remove item from cart if quantity is 0
                    Const.cartItems.removeAt(index)
                    addCart.visibility = View.VISIBLE
                    itemQty.visibility = View.GONE
                }
            } else {
                // Add item to cart if it doesn't exist
                Const.cartItems.add(
                    DummyOrderItems(
                        item.id,
                        item.prdname,
                        item.prdbrand,
                        item.prdcrncy,
                        item.prdmrp,
                        item.prdofferrate,
                        item.prdsellrate,
                        item.prdoffer,
                        quantity.toString(),
                        (item.prdsellrate.toFloat() * quantity).toString()
                    )
                )
            }
            getCartUtil.onAddtocartCallback()
            notifyDataSetChanged()
        }

        private fun handleOfferTextView(item: DummyItem) {
            if (item.prdofferrate == "") {
                itemOffer.visibility = View.GONE
                itemRate.text = item.prdcrncy + item.prdmrp
            } else {
                itemOffer.visibility = View.VISIBLE
                itemRate.text = item.prdcrncy + item.prdofferrate
                val mrpText = "MRP ${item.prdmrp}${item.prdcrncy}"
                val offerText = "${item.prdoffer}% off"
                val spannableString = SpannableString("$mrpText $offerText")
                spannableString.setSpan(
                    StrikethroughSpan(),
                    0,  // Start index of MRP text
                    mrpText.length, // End index of MRP text
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    ForegroundColorSpan(Color.GREEN),
                    mrpText.length + 1,  // Start index of offer text
                    spannableString.length, // End index of offer text
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                itemOffer.text = spannableString
            }
        }
    }
}
