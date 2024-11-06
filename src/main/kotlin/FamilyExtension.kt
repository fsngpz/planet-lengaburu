/**
 * an extension class to find the [Person] using the name.
 *
 * @param name the person name.
 * @return the [Person] or null.
 */
fun Family.findPersonByName(name: String): Person? {
    if (this.husband?.name == name) return this.husband
    if (this.wife?.name == name) return this.wife
    children?.forEach { childFamily ->
        val person = childFamily.findPersonByName(name)
        if (person != null) return person
    }
    return null
}

/**
 * an extension class to find the [Family] by [Person].
 *
 * @param person the [Person].
 * @return the [Family] or null.
 */
fun Family.findFamilyByPerson(person: Person): Family? {
    if (this.husband == person) return this
    if (this.wife == person) return this

    // -- check in children recursively --
    children?.forEach { childFamily ->
        val family = childFamily.findFamilyByPerson(person)
        if (family != null) return family
    }
    return null
}

/**
 * an extension function to find the Children ([Person]) using the name and gender.
 *
 * @param name the name of target family member.
 * @param gender the [Gender] of children.
 * @return the [List] of Children ([Person]).
 */
fun Family.findChildren(name: String, gender: Gender): List<Person> {
    val person = this.findPersonByName(name) ?: return emptyList()
    val family = this.findFamilyByPerson(person) ?: return emptyList()
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

/**
 * an extension function to find the siblings using the target name.
 *
 * @param name the name of target family member.
 * @return the [List] of Siblings ([Person]).
 */
fun Family.findSiblings(name: String): List<Person> {
    val person = this.findPersonByName(name) ?: return emptyList()
    val parentFamily = this.findFamilyByPerson(person)?.parent ?: return emptyList()
    return parentFamily.children?.flatMap { child ->
        listOfNotNull(child.husband, child.wife)
    }?.filter { it.name != name && it.isChildOfParent } ?: emptyList()
}

/**
 * an extension function to find the uncles or aunts with given target name.
 *
 * @param name the name of target family member.
 * @param paternal the boolean to determine find the uncles or aunts from father or not.
 * @param findUncles the boolean to determine find the uncles or aunts.
 * @return the [List] of Uncles or Aunts ([Person]).
 */
fun Family.findUnclesOrAunts(name: String, paternal: Boolean, findUncles: Boolean): List<Person> {
    val person = this.findPersonByName(name) ?: return emptyList()
    val parentFamily = this.findFamilyByPerson(person)?.parent ?: return emptyList()
    val targetParent = if (paternal) parentFamily.husband else parentFamily.wife

    require(targetParent != null && targetParent.isChildOfParent) { return emptyList() }

    val grandParentFamily = this.findFamilyByPerson(targetParent)?.parent
    val targetRelation = if (findUncles) { it: Family -> it.husband } else { it: Family -> it.wife }

    return grandParentFamily?.children
        ?.filter { it != parentFamily }
        ?.mapNotNull(targetRelation) ?: emptyList()
}

/**
 * an extension function to find the Brothers or Sisters in laws.
 *
 * @param name the name of target family member.
 * @return the [List] of name brothers-in-law or sisters-in-law ([Person]).
 */
fun Family.findInLaws(name: String, isBrotherInLaw: Boolean): List<Person> {
    val person = findPersonByName(name) ?: return emptyList()
    val family = findFamilyByPerson(person) ?: return emptyList()

    val inLaws = mutableSetOf<Person>()

    // -- Find from spouse's siblings --
    val spouse = if (family.husband?.name == name) family.wife else family.husband
    if (spouse != null && spouse.isChildOfParent) {
        val spouseFamily = findFamilyByPerson(spouse)
        spouseFamily?.parent?.children?.forEach {
            if (isBrotherInLaw && it.husband != null && it.husband != spouse) {
                inLaws.add(it.husband)
            } else if (it.wife != null && it.wife != spouse) {
                inLaws.add(it.wife)
            }
        }
    }

    // -- Find from siblings' spouses --
    val parentFamily = family.parent
    requireNotNull(parentFamily) { return inLaws.toList() }

    parentFamily.children?.filter {
        it != family
    }?.forEach { siblingFamily ->
        if (isBrotherInLaw && siblingFamily.husband != null && !siblingFamily.husband.isChildOfParent) {
            inLaws.add(siblingFamily.husband)
        } else if (siblingFamily.wife != null && !siblingFamily.wife.isChildOfParent) {
            inLaws.add(siblingFamily.wife)
        }
    }
    return inLaws.toList()
}


/**
 * an extension function to find the relationship of target family member.
 *
 * @param name the name of target family member.
 * @param relationship the [Relationship].s
 * @return the [List] of Family Member Name.
 */
fun Family.findRelationship(name: String, relationship: Relationship): List<Person> {
    return when (relationship) {
        Relationship.SON -> this.findChildren(name, Gender.MALE)
        Relationship.DAUGHTER -> this.findChildren(name, Gender.FEMALE)
        Relationship.SIBLINGS -> this.findSiblings(name)
        Relationship.PATERNAL_UNCLE -> this.findUnclesOrAunts(name, paternal = true, findUncles = true)
        Relationship.MATERNAL_UNCLE -> this.findUnclesOrAunts(name, paternal = false, findUncles = true)
        Relationship.PATERNAL_AUNT -> this.findUnclesOrAunts(name, paternal = true, findUncles = false)
        Relationship.MATERNAL_AUNT -> this.findUnclesOrAunts(name, paternal = false, findUncles = false)
        Relationship.BROTHER_IN_LAW -> this.findInLaws(name, true)
        Relationship.SISTER_IN_LAW -> this.findInLaws(name, false)
    }
}
