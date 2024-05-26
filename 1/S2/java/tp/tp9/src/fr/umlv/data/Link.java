package fr.umlv.data;

record Link<T>(T elt, Link<T> next) {

    public String toString() {
        return elt + (next != null ? "->" + next : "");
    }

    /*Q2, il faut utilise javac sur le .java puis java sur le .class*/
}

