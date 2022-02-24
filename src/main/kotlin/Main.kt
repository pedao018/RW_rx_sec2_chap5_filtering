import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

fun main(args: Array<String>) {

    exampleOf("ignoreElements") {
        val compositeDisposable = CompositeDisposable()
        val strikes = PublishSubject.create<String>()
        compositeDisposable.add(
            strikes
                .ignoreElements()
                .subscribeBy {
                    println("You're out!")
                }
        )
        strikes.onNext("X")
        strikes.onNext("X")
        strikes.onNext("X")
        strikes.onComplete()
    }

    exampleOf("elementAt") {
        val publishSubject = PublishSubject.create<String>()
        publishSubject.elementAt(2)
            .subscribeBy(onSuccess = {
                print("You're out!")
            })
        publishSubject.onNext("X")
        publishSubject.onNext("X")
        publishSubject.onNext("X")
    }

    exampleOf("filter") {
        Observable.fromIterable(listOf(1, 2, 3, 4, 5, 6, 7, 8))
            .filter { number -> number > 5 }
            .subscribe {
                print(it)
            }
        println()

        println("Publish Subject:")
        val publishSubject = PublishSubject.create<Int>()
        publishSubject.filter { number -> number > 5 }
            .subscribe { print(it) }
        publishSubject.filter { number -> number > 2 }
            .toList()
            .subscribeBy(onSuccess = { print(it) })

        publishSubject.onNext(1)
        publishSubject.onNext(5)
        publishSubject.onNext(6)
        publishSubject.onNext(7)
        publishSubject.onNext(8)
        publishSubject.onComplete()

    }

    exampleOf("skip") {
        Observable.just("A", "B", "C", "D", "E", "F")
            .skip(2)
            .subscribe { println(it) }
    }

    exampleOf("skipWhile") {
        Observable.just(2, 2, 3, 4, 2)
            .skipWhile { number -> number % 2 == 0 }
            .subscribe { print(it) }
    }

    exampleOf("skipUntil") {
        val subject = PublishSubject.create<String>()
        val trigger = PublishSubject.create<String>()
        subject.skipUntil(trigger)
            .subscribe { println(it) }
        subject.onNext("A")
        subject.onNext("B")
        trigger.onNext("X")
        subject.onNext("C")
    }

    exampleOf("take") {
        val subscriptions = CompositeDisposable()

        subscriptions.add(
            // 1
            Observable.just(1, 2, 3, 4, 5, 6)
                // 2
                .take(3)
                .subscribe {
                    println(it)
                })
    }

    exampleOf("takeWhile") {
        val subscriptions = CompositeDisposable()

        subscriptions.add(
            // 1
            Observable.fromIterable(
                listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1)
            )
                // 2
                .takeWhile { number ->
                    number < 5
                }.subscribe {
                    println(it)
                })
    }

    exampleOf("takeUntil") {
        val subscriptions = CompositeDisposable()
        // 1
        val subject = PublishSubject.create<String>()
        val trigger = PublishSubject.create<String>()

        subscriptions.add(
            // 2
            subject.takeUntil(trigger)
                .subscribe {
                    println(it)
                })
        // 3
        subject.onNext("1")
        subject.onNext("2")
        trigger.onNext("X")
        subject.onNext("4")
    }

    exampleOf("distinctUntilChanged") {
        val subscriptions = CompositeDisposable()

        subscriptions.add(
            // 1
            Observable.just("Dog", "Cat", "Cat", "Dog")
                // 2
                .distinctUntilChanged()
                .subscribe {
                    println(it)
                })
    }

    exampleOf("distinctUntilChangedPredicate") {
        val subscriptions = CompositeDisposable()

        subscriptions.add(
            // 1
            Observable.just(
                "ABC", "BCD", "CDE", "FGH", "IJK", "JKL", "LMN"
            )
                // 2
                .distinctUntilChanged { first, second ->
                    // 3
                    second.any { it in first }
                }
                // 4
                .subscribe {
                    println(it)
                }
        )
    }

}
