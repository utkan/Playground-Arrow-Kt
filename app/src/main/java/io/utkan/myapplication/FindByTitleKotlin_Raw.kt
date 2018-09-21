package io.utkan.myapplication

class FindByTitleKotlin_Raw {
    companion object {
        //<editor-fold desc="Imperative">
        //        fun findByTitle(query: String, collection: MutableList<Movie>): List<Movie> {
//            val results: MutableList<Movie> = mutableListOf()
//            do {
//                val movie: Movie = collection.removeAt(0)
//                if (movie.title.contains(query)) {
//                    results.add(movie)
//                }
//            } while (collection.size > 0)
//            return results
//        }
        //</editor-fold>

        //<editor-fold desc="Functional">
        /*
        Currying: technique of translating the evaluation of a function that
        takes multiple arguments into evaluating a sequence of functions,
        each with a single argument — wikipedia
        Currying enables lambda calculus which you will probably never use in standard apps but
        also permits to reduce functions to less complex ones which may already exist in your SDK
        and then reduce your code.

Partial application is a similar concept without the 1-argument requirement.

Partial application: refers to the process of fixing a number of arguments to a function, producing another function of smaller arity. — wikipedia
        * */
        fun findByTitle_Pure(query: String, collection: MutableList<Movie>): List<Movie> {
            var results: List<Movie> = listOf()

            val predicate = ::matches
//            val add = fun(movie: Movie, movies:List<Movie>) = movies.plus(movie)
            // Currying
            val add = fun(movie: Movie) = fun(movies: List<Movie>) = movies.plus(movie)
            collection.forEach { movie ->
                //                val fn = addIf_P(predicate, query, movie, add)
                val fn = addIf_P_Curry(predicate, query, movie, add)
//                results = fn(movie, results)
                results = fn(movie)(results)
            }
            return results
        }

        private fun addIf_P_Curry(
                predicate: (String, Movie) -> Boolean,
                query: String,
                movie: Movie,
                add: (Movie) -> (List<Movie>) -> List<Movie>): (Movie) -> (List<Movie>) -> (List<Movie>) {
            if (predicate(query, movie)) {
                return add
            }
//            return fun (movie:Movie, movies:List<Movie>) = listOf<Movie>()
            return fun(movie: Movie) = fun(movies: List<Movie>) = listOf<Movie>()
        }

        private fun addIf_P(
                predicate: (String, Movie) -> Boolean,
                query: String,
                movie: Movie,
                add: (Movie, List<Movie>) -> (List<Movie>)): (Movie, List<Movie>) -> (List<Movie>) {
            if (predicate(query, movie)) {
                return add
            }
            return fun(movie: Movie, movies: List<Movie>) = listOf<Movie>()
        }

        // add() uses a parameter, results, which is not passed as an argument.
        // This parameter is called a closed parameter and the method a closure.
        // The parameter is said closed because not modifiable by argument,
        // by opposition to an open parameter like those passed as standard method argument.
        fun findByTitle(query: String, collection: MutableList<Movie>): List<Movie> {
            val results: MutableList<Movie> = mutableListOf()

            val predicate = ::matches
            val add = fun(movie: Movie) = results.add(movie)
            collection.forEach { movie ->
                val fn = addIf(predicate, query, movie, add)
                fn(movie)
            }
            return results
        }

        private fun addIf(
                predicate: (String, Movie) -> Boolean,
                query: String,
                movie: Movie,
                add: (Movie) -> (Boolean)): (Movie) -> (Boolean) {
            if (predicate(query, movie)) {
                return add
            }
            return fun(movie: Movie) = false
        }

        // TODO: Side effect (results, modified)
        private fun addIfMatches(query: String, movie: Movie, results: MutableList<Movie>) {
            if (matches(query, movie)) {
                results.add(movie)
            }
        }

        private fun matches(query: String, movie: Movie): Boolean {
            return title(movie).isInfixOf(query)
        }

        private fun title(movie: Movie): String {
            return movie.title
        }

        private fun String.isInfixOf(query: String): Boolean {
            return contains(query)
        }
        //</editor-fold>
    }
}