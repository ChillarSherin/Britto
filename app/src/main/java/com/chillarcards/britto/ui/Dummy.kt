package com.chillarcards.britto.ui

data class Dummy( val id: Int,val name: String, val type: String)
data class DummyImage( val id: Int,val name: String)
data class DummyDrawer( val id: Int, val image: Int,val name: String)
data class DummyMenu( val id: Int,val image: String,val name: String,val title: String)
data class DummyJob( val id: Int,val image: String,val jobname: String,val comname: String,val jonloc: String,val posted: String,val categ: String)
data class DummyItem( val id: Int,val prdname: String,val prdbrand: String,val prdcrncy: String,val prdmrp: String,val prdofferrate: String,val prdsellrate: String,val prdoffer: String,var cartQty: String)
data class DummyOrderItems(val id: Int, val prdname: String, val prdbrand: String, val prdcrncy: String, val prdmrp: String, val prdofferrate: String,val prdsellrate: String, val prdoffer: String,
                           var cartQty: String,var cartRate: String)
