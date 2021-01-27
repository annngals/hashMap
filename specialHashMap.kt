class SpecialHashMap(): HashMap<String, Int>() {

    public val iloc: ArrayList<Int> = ArrayList()

    override fun put(key: String, value: Int): Int? {
        val result = super.put(key, value)
        iloc.clear()

        val keys = keys.toSortedSet { s1, s2 -> s1.compareTo(s2) }

        for (key in keys) {
            this[key]?.let { iloc.add(it) }
        }
        return result
    }

    public val ploc: Ploc = Ploc()

    @Suppress("DEPRECATION")
    inner class Ploc() {

        private fun signToCondition(strCondition: String): Condition {

            return when {
                strCondition.startsWith(">=") -> Condition(strCondition.drop(2).toInt(), Sign.MORE_EQ)

                strCondition.startsWith("<=") -> Condition(strCondition.drop(2).toInt(), Sign.LESS_EQ)

                strCondition.startsWith("<>") -> Condition(strCondition.drop(2).toInt(), Sign.NOT_EQ)

                strCondition.startsWith("=") -> Condition(strCondition.drop(1).toInt(), Sign.EQUALS)

                strCondition.startsWith(">") -> Condition(strCondition.drop(1).toInt(), Sign.MORE)

                strCondition.startsWith("<") -> Condition(strCondition.drop(1).toInt(), Sign.LESS)

                else -> error("Unable to recognize conditions")
            }
        }

        operator fun get(conditions: String): Map<String, Int> {

            val regex = "[<>=]{1,2}[0-9]+".toRegex()

            val conditionsArr = regex.findAll(conditions).map { signToCondition(it.value) }.toList()

            val result: MutableMap<String, Int> = mutableMapOf()

            for (key in keys) {

                var key1 = key.strip()

                if (!key1.startsWith('(') || !(key1.endsWith(')'))) {

                    if (conditionsArr.size != 1) continue

                    val isCorrect = key1.toIntOrNull()?.let { conditionsArr.first().compare(it) } ?: continue

                    if (isCorrect) {
                        this@SpecialHashMap[key]?.let { result.put(key, it) }
                    }

                    continue
                }

                key1 = key1.drop(1).dropLast(1)

                val splitValues = key1.split(",").map { it.strip().toIntOrNull() }

                if (splitValues.size != conditionsArr.size) continue

                val isCorrect = splitValues.zip(conditionsArr).all{ (value, condition) -> condition.compare(value!!) }

                if (isCorrect) {
                    this@SpecialHashMap[key]?.let { result.put(key, it) }
                }
            }
            return result
        }
    }
}