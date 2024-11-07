/**
 * The class represent the logic of Adding new children and getting the relationship.
 *
 * @author Ferdinand Sangap.
 * @since 2024-11-06
 */
class FamilyTree {
    val kingArthurFamily = generateFamilyOfKingArthur()

    /**
     * a method to find add the child using the mother name and gender.
     *
     * @param motherName the mother name.
     * @param name the name of child.
     * @param gender the gender of child.
     * @return the String represent the response.
     */
    fun addChild(motherName: String, name: String, gender: Gender): String {
        val parent = kingArthurFamily.findPersonByName(motherName)
        requireNotNull(parent) {
            return "PERSON_NOT_FOUND"

        }
        val husband = if (gender == Gender.MALE) Person(name, true) else null
        val wife = if (gender == Gender.FEMALE) Person(name, true) else null

        val parentFamily = kingArthurFamily.findFamilyByPerson(parent)
        require(parentFamily?.wife == parent) {
            return "CHILD_ADDITION_FAILED"
        }

        val newFamily = Family(husband, wife, parentFamily)
        // -- add the new family (children) to the parent family --
        parentFamily?.apply {
            this.children?.add(newFamily)
        }
        return "CHILD_ADDED"
    }

    /**
     * a method to find the name of Family member with given target name and relationship.
     *
     * @param name the target family member name.
     * @param relationship the [Relationship].
     * @return the List of Family Member Name.
     */
    fun getRelationShip(name: String, relationship: Relationship): List<String> {
        val person = kingArthurFamily.findRelationship(name, relationship)
        // -- map the name of person --
        return person.map { it.name }
    }

    /**
     * The method to generate the King Arthur Family.
     *
     * @return the Family of King Arthur.
     */
    private fun generateFamilyOfKingArthur(): Family {
        // -- create Family of King Arthur and Queen Margaret and set the children to null --
        // -- the parent should be null because there is no parent of King Arthur Family --
        val kingArthur = Person("Arthur")
        val queenMargareth = Person("Margareth")
        val kingArthurFamily = Family(kingArthur, queenMargareth, null)

        // -- create Family of Bill and Flora --
        val bill = Person("Bill", true)
        val flora = Person("Flora")
        val billFamily = Family(bill, flora, kingArthurFamily)
        generateFamilyOfBill(billFamily)

        // -- create Family of Charlie --
        val charlie = Person("Charlie", true)
        val charlieFamily = Family(charlie, null, kingArthurFamily)

        // -- create Family of Percy --
        val percy = Person("Percy", true)
        val audrey = Person("Audrey")
        val percyFamily = Family(percy, audrey, kingArthurFamily)
        generateFamilyOfPercy(percyFamily)

        // -- create Family of Ronald --
        val ronald = Person("Ronald", true)
        val helen = Person("Helen")
        val ronaldFamily = Family(ronald, helen, kingArthurFamily)
        generateFamilyOfRonald(ronaldFamily)

        // -- create Family of Ginerva --
        val ginerva = Person("Ginerva", true)
        val harry = Person("Harry")
        val ginervaFamily = Family(harry, ginerva, kingArthurFamily)
        generateFamilyOfGinerva(ginervaFamily)

        // -- set  the children of King Arthur --
        val kingArthurChildren = mutableListOf(billFamily, charlieFamily, percyFamily, ronaldFamily, ginervaFamily)
        kingArthurFamily.apply { this.children = kingArthurChildren }
        return kingArthurFamily
    }

    private fun generateFamilyOfBill(parent: Family) {
        // -- create Family of Victorie --
        val victorie = Person("Victorie", true)
        val ted = Person("Ted")
        val victoriaFamily = Family(ted, victorie, parent)
        generateFamilyOfVictorie(victoriaFamily)

        // -- create Family of Dominique --
        val dominique = Person("Dominique", true)
        val dominiqueFamily = Family(null, dominique, parent)

        // -- create Family of Louis --
        val louis = Person("Louis", true)
        val louisFamily = Family(louis, null, parent)

        // -- set  the children of parent --
        val billChildren = mutableListOf(victoriaFamily, dominiqueFamily, louisFamily)
        parent.apply { this.children = billChildren }
    }

    private fun generateFamilyOfPercy(parent: Family) {
        // -- create Family of Molly --
        val molly = Person("Molly", true)
        val mollyFamily = Family(null, molly, parent)

        // -- create Family of Lucy --
        val lucy = Person("Lucy", true)
        val lucyFamily = Family(null, lucy, parent)

        // -- set  the children of parent --
        val percyChildren = mutableListOf(mollyFamily, lucyFamily)
        parent.apply { this.children = percyChildren }
    }

    private fun generateFamilyOfRonald(parent: Family) {
        // -- create Family of Malfoy --
        val malfoy = Person("Malfoy")
        val rose = Person("Rose", true)
        val roseFamily = Family(malfoy, rose, parent)
        generateFamilyOfRose(roseFamily)

        // -- create Family of Lucy --
        val hugo = Person("Hugo", true)
        val hugoFamily = Family(hugo, null, parent)

        // -- set  the children of parent --
        val ronaldChildren = mutableListOf(roseFamily, hugoFamily)
        parent.apply { this.children = ronaldChildren }
    }

    private fun generateFamilyOfGinerva(parent: Family) {
        // -- create Family of James --
        val james = Person("James", true)
        val darcy = Person("Darcy")
        val jamesFamily = Family(james, darcy, parent)
        generateFamilyOfJames(jamesFamily)

        // -- create Family of Albus --
        val albus = Person("Albus", true)
        val alice = Person("Alice")
        val albusFamily = Family(albus, alice, parent)
        generateFamilyOfAlbus(albusFamily)

        // -- create Family of Lily --
        val lily = Person("Lily", true)
        val lilyFamily = Family(null, lily, parent)

        // -- set the children of Ginerva and Harry --
        val ginervaChildren = mutableListOf(jamesFamily, albusFamily, lilyFamily)
        parent.apply { this.children = ginervaChildren }
    }

    private fun generateFamilyOfVictorie(parent: Family) {
        // -- create Family of Remus --
        val remus = Person("Remus", true)
        val remusFamily = Family(remus, null, parent)

        // -- set the children of Victorie and Ted --
        val victorieChildren = mutableListOf(remusFamily)
        parent.apply { this.children = victorieChildren }
    }

    private fun generateFamilyOfRose(parent: Family) {
        // -- create Family of Draco --
        val draco = Person("Draco", true)
        val dracoFamily = Family(draco, null, parent)

        // -- create Family of Aster --
        val aster = Person("Aster", true)
        val asterFamily = Family(null, aster, parent)

        // -- set the children of Malfoy and Rose --
        val roseChildren = mutableListOf(dracoFamily, asterFamily)
        parent.apply { this.children = roseChildren }
    }

    private fun generateFamilyOfJames(parent: Family) {
        // -- create Family of William --
        val william = Person("William", true)
        val williamFamily = Family(william, null, parent)

        // -- set the children of Darcy and James --
        val jamesChildren = mutableListOf(williamFamily)
        parent.apply { this.children = jamesChildren }
    }

    private fun generateFamilyOfAlbus(parent: Family) {
        // -- create Family of Ron --
        val ron = Person("Ron", true)
        val ronFamily = Family(ron, null, parent)

        // -- create Family of Ginny --
        val ginny = Person("Ginny", true)
        val ginnyFamily = Family(null, ginny, parent)

        // -- set the children of Alice and Albus --
        val albusChildren = mutableListOf(ronFamily, ginnyFamily)
        parent.apply { this.children = albusChildren }
    }
}
