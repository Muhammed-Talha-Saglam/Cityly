package dev.bytecode.cityly.data.model

data class City(
   val name: String,
   val costOfLiving: Double,
   val safety: Double,
   val internetAccess: Double,
   val leisureCulture: Double,
   val tolerance: Double,
   val result: Double
): Comparable<City>{
   override fun compareTo(other: City): Int {
      return  when {
         result > other.result -> 1
         result < other.result -> -1
         else -> 0
      }
   }

}