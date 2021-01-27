fun main(args: Array<String>) {
    test1()
    test2()
}

fun test1() {
    val map = SpecialHashMap()
    map["value1"] = 1
    map["value2"] = 2
    map["value3"] = 3
    map["1"] = 10
    map["2"] = 20
    map["3"] = 30
    map["1, 5"] = 100
    map["5, 5"] = 200
    map["10, 5"] = 300
    println(map.iloc[0])  // >>> 10
    println(map.iloc[2])  // >>> 300
    println(map.iloc[5])  // >>> 200
    println(map.iloc[8])  // >>> 3
}

fun test2() {
    val map = SpecialHashMap()
    map["value1"] = 1
    map["value2"] = 2
    map["value3"] = 3
    map["1"] = 10
    map["2"] = 20
    map["3"] = 30
    map["(1, 5)"] = 100
    map["(5, 5)"] = 200
    map["(10, 5)"] = 300
    map["(1, 5, 3)"] = 400
    map["(5, 5, 4)"] = 500
    map["(10, 5, 5)"] = 600

    println(map.ploc[">=1"]) // >>> {1=10, 2=20, 3=30}
    println(map.ploc["<3"]) // >>> {1=10, 2=20}

    println(map.ploc[">0, >0"]) // >>> {(1, 5)=100, (5, 5)=200, (10, 5)=300}
    println(map.ploc[">=10, >0"]) // >>> {(10, 5)=300}

    println(map.ploc["<5, >=5, >=3"]) // >>> {(1, 5, 3)=400}
}