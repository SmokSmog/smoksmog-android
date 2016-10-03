package pl.malopolska.smoksmog


import pl.malopolska.smoksmog.model.Particulate
import rx.Observable
import rx.functions.Func1
import rx.functions.Func2

class ApiUtils private constructor() {

    init {
        throw IllegalAccessError()
    }

    companion object {

        fun sortParticulates(particulates: Collection<Particulate>): Observable<Particulate> {

            return Observable.from(particulates)
                    .toSortedList { first, second -> first.position - second.position }
                    .flatMap { particulates -> Observable.from(particulates) }
        }
    }
}
