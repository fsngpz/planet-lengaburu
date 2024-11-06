/**
 * @author Ferdinand Sangap.
 * @since 2024-11-06
 */
fun main() {
    val familyTree = FamilyTree()
    println()
    println()
    println("Meet the Family of King Arthur")
    println()
    println("ADD_CHILD Flora Minerva Female")
    println(familyTree.addChild("Flora", "Minerva", Gender.FEMALE))
    println()

    println("GET_RELATIONSHIP Remus MATERNAL_AUNT")
    println(familyTree.getRelationShip("Remus", Relationship.MATERNAL_AUNT))
    println()

    println("ADD_CHILD Minerva SIBLINGS")
    println(familyTree.getRelationShip("Minerva", Relationship.SIBLINGS))
    println()

    println("ADD_CHILD Luna Lola Female")
    println(familyTree.addChild("Lola", "Luna", Gender.FEMALE))
    println()

    println("ADD_CHILD Ted Bella Female")
    println(familyTree.addChild("Ted", "Bella", Gender.FEMALE))
    println()

    println("GET_RELATIONSHIP Remus Siblings")
    println(familyTree.getRelationShip("Remus", Relationship.SIBLINGS))
    println()

    println("GET_RELATIONSHIP Remus Siblings")
    println(familyTree.getRelationShip("Lily", Relationship.SISTER_IN_LAW))
    println()
}
