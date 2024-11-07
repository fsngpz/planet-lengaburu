import org.junit.jupiter.api.Test

/**
 * The test class for [FamilyTree].
 *
 * @author Ferdinand Sangap.
 * @since 2024-11-07
 */
class FamilyTreeTest {
    private val familyTree = FamilyTree()

    @Test
    fun `findPersonByName, find the name of Parent success`() {
        val name = "Arthur"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findPersonByName(name)
        requireNotNull(result)
        assert(result.name == name)
    }

    @Test
    fun `findPersonByName, find the name of Children success`() {
        val name = "Remus"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findPersonByName(name)
        requireNotNull(result)
        assert(result.name == name)
    }

    @Test
    fun `findPersonByName, not found`() {
        val name = "Mega"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findPersonByName(name)
        assert(result == null)
    }

    @Test
    fun `findFamilyByPerson, find third layer family, success`() {
        val name = "Aster"
        val kingArthurFamily = familyTree.kingArthurFamily

        val person = kingArthurFamily.findPersonByName(name)
        requireNotNull(person)
        val result = kingArthurFamily.findFamilyByPerson(person)
        assert(result != null)
    }

    @Test
    fun `findFamilyByPerson, not found`() {
        val kingArthurFamily = familyTree.kingArthurFamily

        val person = Person("Unit Test")

        val result = kingArthurFamily.findFamilyByPerson(person)
        assert(result == null)
    }

    @Test
    fun `findChildren (SON), not found`() {
        val name = "Aster"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findChildren(name, Gender.MALE)
        assert(result.isEmpty())
    }

    @Test
    fun `findChildren (DAUGHTER), not found`() {
        val name = "Hugo"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findChildren(name, Gender.MALE)
        assert(result.isEmpty())
    }

    @Test
    fun `findChildren (DAUGHTER), success and found`() {
        val name = "Audrey"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findChildren(name, Gender.FEMALE)
        assert(result.isNotEmpty())
        assert(result.size == 2)
    }

    @Test
    fun `findChildren (SON), success and found`() {
        val name = "Bill"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findChildren(name, Gender.MALE)
        assert(result.isNotEmpty())
        assert(result.size == 1)
    }

    @Test
    fun `findSiblings, no siblings`() {
        val name = "Remus"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findSiblings(name)
        assert(result.isEmpty())
    }

    @Test
    fun `findSiblings, with siblings`() {
        val name = "Ginerva"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findSiblings(name)
        assert(result.isNotEmpty())
        assert(result.size == 4)
    }

    @Test
    fun `findUnclesOrAunts Paternal, no uncle`() {
        val name = "Ginerva"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findUnclesOrAunts(name, paternal = true, findUncles = true)
        assert(result.isEmpty())
    }

    @Test
    fun `findUnclesOrAunts Maternal, no uncle`() {
        val name = "Ginerva"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findUnclesOrAunts(name, paternal = false, findUncles = true)
        assert(result.isEmpty())
    }

    @Test
    fun `findUnclesOrAunts Maternal, no Aunt`() {
        val name = "Ronald"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findUnclesOrAunts(name, paternal = false, findUncles = false)
        assert(result.isEmpty())
    }

    @Test
    fun `findUnclesOrAunts Paternal, no Aunt`() {
        val name = "Bill"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findUnclesOrAunts(name, paternal = false, findUncles = false)
        assert(result.isEmpty())
    }

    @Test
    fun `findUnclesOrAunts Paternal, Uncles found`() {
        val name = "Victorie"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findUnclesOrAunts(name, paternal = true, findUncles = true)
        assert(result.isNotEmpty())
        assert(result.size == 4)
    }

    @Test
    fun `findUnclesOrAunts Paternal, Aunties found`() {
        val name = "Victorie"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findUnclesOrAunts(name, paternal = true, findUncles = false)
        assert(result.isNotEmpty())
        assert(result.size == 3)
    }

    @Test
    fun `findUnclesOrAunts Maternal, Uncles found`() {
        val name = "Remus"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findUnclesOrAunts(name, paternal = false, findUncles = true)
        assert(result.isNotEmpty())
        assert(result.size == 1)
    }

    @Test
    fun `findUnclesOrAunts Maternal, Aunties found`() {
        val name = "Remus"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findUnclesOrAunts(name, paternal = false, findUncles = false)
        assert(result.isNotEmpty())
        assert(result.size == 1)
    }

    @Test
    fun `findInLaws, from outside family, Brother in Law found`() {
        val name = "Harry"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findInLaws(name, true)
        assert(result.isNotEmpty())
        assert(result.size == 4)
    }

    @Test
    fun `findInLaws, from inside family, Brother in Law found`() {
        val name = "Hugo"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findInLaws(name, true)
        assert(result.isNotEmpty())
        assert(result.size == 1)
    }

    @Test
    fun `findInLaws, Sister in Law found`() {
        val name = "Harry"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findInLaws(name, false)
        assert(result.isNotEmpty())
        assert(result.size == 3)
    }

    @Test
    fun `findInLaws, Brother in Law not found`() {
        val name = "Lucy"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findInLaws(name, true)
        assert(result.isEmpty())

    }

    @Test
    fun `findInLaws, Sister in Law not found`() {
        val name = "Lucy"
        val kingArthurFamily = familyTree.kingArthurFamily

        val result = kingArthurFamily.findInLaws(name, false)
        assert(result.isEmpty())
    }

    @Test
    fun `addChild, person not found`() {
        val result = familyTree.addChild("Pona", "Nebraska", Gender.MALE)
        assert(result == "PERSON_NOT_FOUND")
    }

    @Test
    fun `addChild, failed to add because given father name`() {
        val result = familyTree.addChild("Harry", "Nebraska", Gender.MALE)
        assert(result == "CHILD_ADDITION_FAILED")
    }

    @Test
    fun `addChild, success to add new Boy`() {
        val motherName = "Darcy"
        // -- validate the children before add the new child --
        val mother = familyTree.kingArthurFamily.findPersonByName(motherName)
        requireNotNull(mother)
        var family = familyTree.kingArthurFamily.findFamilyByPerson(mother)
        assert(family?.children?.size == 1)

        val result = familyTree.addChild(motherName, "Nebraska", Gender.MALE)
        assert(result == "CHILD_ADDED")
        // -- validate the children after add the new child --
        family = familyTree.kingArthurFamily.findFamilyByPerson(mother)
        assert(family?.children?.size == 2)
    }

    @Test
    fun `getRelationShip, siblings but empty`() {
        val result = familyTree.getRelationShip("Remus", Relationship.SIBLINGS)
        assert(result.isEmpty())
    }

    @Test
    fun `getRelationShip, sister in laws found`() {
        val result = familyTree.getRelationShip("Lily", Relationship.SISTER_IN_LAW)
        assert(result.size == 2)
    }

    @Test
    fun `getRelationShip, son found`() {
        val result = familyTree.getRelationShip("Arthur", Relationship.SON)
        assert(result.size == 4)
    }

    @Test
    fun `getRelationShip, daughter found`() {
        val result = familyTree.getRelationShip("Malfoy", Relationship.DAUGHTER)
        assert(result.size == 1)
    }

    @Test
    fun `getRelationShip, siblings found`() {
        val result = familyTree.getRelationShip("Draco", Relationship.SIBLINGS)
        assert(result.size == 1)
    }

    @Test
    fun `getRelationShip, brother in law found`() {
        val result = familyTree.getRelationShip("Malfoy", Relationship.BROTHER_IN_LAW)
        assert(result.size == 1)
    }

    @Test
    fun `getRelationShip, paternal uncle`() {
        val result = familyTree.getRelationShip("William", Relationship.PATERNAL_UNCLE)
        assert(result.size == 1)
    }

    @Test
    fun `getRelationShip, paternal aunt`() {
        val result = familyTree.getRelationShip("William", Relationship.PATERNAL_AUNT)
        assert(result.size == 2)
    }

    @Test
    fun `getRelationShip, maternal uncle`() {
        val result = familyTree.getRelationShip("Albus", Relationship.MATERNAL_UNCLE)
        assert(result.size == 4)
    }

    @Test
    fun `getRelationShip, maternal aunt`() {
        val result = familyTree.getRelationShip("Albus", Relationship.MATERNAL_AUNT)
        assert(result.size == 3)
    }
}
