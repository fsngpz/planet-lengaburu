/**
 * @author Ferdinand Sangap.
 * @since 2024-11-06
 */
fun main() {
    val familyTree = FamilyTree()

    while (true) {
        println("Meet the Family of King Arthur")
        println("To Add Child the new Family please follow this format [ADD_CHILD Mother-Name Child-Name Gender]")
        println("To Find Relationship of the Family please follow this format [GET_RELATIONSHIP Name Relationship]")
        val input = readlnOrNull()
        val inputs = input?.split(' ')

        val type = inputs?.first()
        when (type) {
            "ADD_CHILD" -> {
                val motherName = inputs[1]
                val childName = inputs[2]
                val gender = Gender.valueOf(inputs[3].uppercase())
                val result = familyTree.addChild(motherName, childName, gender)
                println(result)
            }

            "GET_RELATIONSHIP" -> {
                val name = inputs[1]
                val relationship = Relationship.valueOf(inputs[2].uppercase())
                val result = familyTree.getRelationShip(name, relationship)
                println(result)
            }

            else -> println("Unknown Input: $type")
        }

        println()
        println()
    }
}
