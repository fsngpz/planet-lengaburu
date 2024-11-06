/**
 * The data class represent the entity of Family.
 *
 * @author Ferdinand Sangap.
 * @since 2024-11-06
 */
data class Family(
    val husband: Person?,
    val wife: Person?,
    val parent: Family?
) {
    var children: MutableList<Family>? = null

    fun findPersonByName(name: String): Person? {
        if (husband?.name == name) return husband
        if (wife?.name == name) return wife
        children?.forEach { childFamily ->
            val person = childFamily.findPersonByName(name)
            if (person != null) return person
        }
        return null
    }

    fun findFamilyByPerson(person: Person): Family? {
        if (this.husband == person) return this
        if (this.wife == person) return this

        // -- check in children recursively --
        children?.forEach { childFamily ->
            val family = childFamily.findFamilyByPerson(person)
            if (family != null) return family
        }
        return null
    }

    fun findRelationship(name: String, relationship: Relationship): List<Person> {
        return when (relationship) {
            Relationship.SON -> findChildren(name, Gender.MALE)
            Relationship.DAUGHTER -> findChildren(name, Gender.FEMALE)
            Relationship.SIBLINGS -> findSiblings(name)
            Relationship.PATERNAL_UNCLE -> findUnclesOrAunts(name, paternal = true, findUncles = true)
            Relationship.MATERNAL_UNCLE -> findUnclesOrAunts(name, paternal = false, findUncles = true)
            Relationship.PATERNAL_AUNT -> findUnclesOrAunts(name, paternal = true, findUncles = false)
            Relationship.MATERNAL_AUNT -> findUnclesOrAunts(name, paternal = false, findUncles = false)
            else -> emptyList()
        }
    }

    private fun findChildren(name: String, gender: Gender): List<Person> {
        val person = findPersonByName(name) ?: return emptyList()
        val family = findFamilyByPerson(person) ?: return emptyList()
        // -- find the children based on gender --
        val children = family.children?.mapNotNull {
            if (gender == Gender.MALE) {
                it.husband
            } else {
                it.wife
            }
        }
        return children?.filter { it.isChildOfParent } ?: emptyList()
    }

    private fun findSiblings(name: String): List<Person> {
        val person = findPersonByName(name) ?: return emptyList()
        val parentFamily = findFamilyByPerson(person)?.parent ?: return emptyList()
        return parentFamily.children?.flatMap { child ->
            listOfNotNull(child.husband, child.wife)
        }?.filter { it.name != name && it.isChildOfParent } ?: emptyList()
    }

    private fun findUnclesOrAunts(name: String, paternal: Boolean, findUncles: Boolean): List<Person> {
        val person = findPersonByName(name) ?: return emptyList()
        val parentFamily = findFamilyByPerson(person)?.parent ?: return emptyList()
        val targetParent = if (paternal) parentFamily.husband else parentFamily.wife

        require(targetParent != null && targetParent.isChildOfParent) { return emptyList() }

        val grandParentFamily = findFamilyByPerson(targetParent)?.parent
        val targetRelation = if (findUncles) { it: Family -> it.husband } else { it: Family -> it.wife }

        return grandParentFamily?.children
            ?.filter { it != parentFamily }
            ?.mapNotNull(targetRelation) ?: emptyList()
    }

}
