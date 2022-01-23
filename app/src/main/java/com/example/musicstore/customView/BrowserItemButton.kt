package com.example.musicstore.customView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musicstore.service.DataBaseSupport
import com.example.musicstore.R

@SuppressLint("SetTextI18n")
class BrowserItemButton(context: Context?) : ConstraintLayout(context!!) {
    private var name: TextView
    private var company: TextView
    private var ilosc: TextView
    private var confirm: Button
    private var plus: Button
    private var minus: Button
    private lateinit var kod: String
    private var iloscNmbr: Int = 0
    private var idZamowienia: Int = 0
    private val status: ArrayList<String> =
        arrayListOf("Do wysyłki", "Zrealizowane", "Anulowano", "Oczewkiwanie na wplate", "Zarezerwowano")
    private var type = false

    init {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.browser_item_button, this, true)
        name = view.findViewById(R.id.productName)
        company = view.findViewById(R.id.company)
        ilosc = view.findViewById(R.id.priceText)
        confirm = view.findViewById(R.id.confirmButton)
        plus = view.findViewById(R.id.plusButton)
        minus = view.findViewById(R.id.minusButton)

        plus.setOnClickListener {
            animateInOut(plus)
            if (type) {
                if (iloscNmbr < status.size - 1) {
                    iloscNmbr++
                    this.company.text = "Status: ${status[iloscNmbr]}"
                }
            } else {
                iloscNmbr++
                this.ilosc.text = "$iloscNmbr szt"
            }
        }

        minus.setOnClickListener {
            animateInOut(minus)
            if (iloscNmbr >= 1) {
                iloscNmbr--
                if (type)
                    this.company.text = "Status: ${status[iloscNmbr]}"
                else
                    this.ilosc.text = "$iloscNmbr szt"
            }
        }

        confirm.setOnClickListener {
            animateInOut(confirm)
            if (type) {
                context.let { it1 -> DataBaseSupport.updateOrderStatus(it1, iloscNmbr, idZamowienia) }
            } else {
                context.let { it1 -> DataBaseSupport.updateItemInStore(it1, iloscNmbr, kod) }
            }
        }
    }

    private fun animateInOut(button: View) {
        val zoomIn: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomin)
        val zoomOut: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomout)
        button.startAnimation(zoomIn)
        button.startAnimation(zoomOut)
    }


    @SuppressLint("SetTextI18n")
    fun setText(name: String, kod: String, ilosc: String) {
        this.name.text = name
        this.company.text = "Kod kreskowy: $kod"
        this.ilosc.text = "$ilosc szt"
        this.iloscNmbr = ilosc.toInt()
    }

    @SuppressLint("SetTextI18n")
    fun setText(id: Int, stat: String, price: Int) {
        this.idZamowienia = id
        this.name.text = "Id zamówienia: $id"
        this.company.text = "Status: $stat"
        this.ilosc.text = "Wartość: $price"
    }

    fun setKod(kod: String) {
        this.kod = kod
    }

    fun setType(boolean: Boolean) {
        this.type = boolean
    }

}