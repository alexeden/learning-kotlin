# General

- The initialization code for an `Activity` or `Fragment` will typically set the content view or "inflate" the UI layout file; in other words, the code opens and parses the XML layout file, then creates and configures all of the view objects in memory

# Android Jetpack

## Data Binding

> 4 components involved in enabling data binding

### 1. Build configuration

Data binding must be enabled in the module-level `build.gradle` file:

```groovy
dataBinding {
  enabled = true
}
```

### 2. Data binding layout (XML) file

The app's root component must be of type `<layout>` .

UI controllers and `ViewModel`s are bound using instances declared by `<variable>`s in a `<data>` element.

A variable must have a name, and a type, which is the full package name of the class instance to be bound (`mainViewModel` below).

Methods of a class can also be called by importing it, which allows you to transform a value before it's display (`MyFormattingTools` below).

```xml
<!-- main_fragment.xml -->
<data>
  <import type="com.example.MyFormattingTools">
  <variable
    name="mainViewModel"
    type="com.example.currencyconverterjetpack.ui.main.MainViewModel" />
  <variable
    name="uiController"
    type="com.example.currencyconverterjetpack.ui.main.MainFragment" />
</data>
```

### 3. Data binding classes

#### Binding class generation

For each class referenced in `<data>`, a _binding class_ will be generated.

A binding class is a subclass of `ViewDataBinding`. They're named based on their layout filename plus the suffix `Binding`.

So, above, the binding class will be called `MainFragmentBinding` and usable in the associated fragment source file.

A binding class contains all of the bindings specified within the layout file and maps them to the variables and methods within the binding target (in this case, an instance of `MainFragment`).

#### Using the binding class

Given a binding class `MainFragmentBinding`, we can instante it using the `DataBindingUtil` class. Instantiation looks slightly different depending on if the view is an `Activity` or a `Fragment`.

```kotlin
// Activity
lateinit var binding: MainFragmentBinding

override fun onCreate() {
  binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
}

// Fragment
lateinit var binding: MainFragmentBinding

override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
): View {
  binding = DataBindingUtil.inflate(inflate, R.layout.main_fragment, container, false)
  // OR
  binding = MainFragmentBinding.inflate(inflater, container, false)
  binding.setLifecycleOwner(this)

  return binding.root
}
```

#### Wiring up the `<variable>`s

The variables in `<variable>` know what type of instance they are and what to call themselves, but they still need to be explicitly set in their corresponding View.

This is done using the `setVariable` method of the data binding instance of type `MainFragmentBinding`.

```kotlin
binding.mainViewModel = viewModel
binding.uiController = this
```

### 4. Data binding expression language

#### One-way binding

I can bind the text displayed in the UI to a piece of LiveData in the view model like this:

```xml
<TextView android:text="@{String.valueOf(safeUnbox(mainViewModel.result))}" />
```

> Values should generally be wrapped by `safeUnbox()` since values might take the form of Java primitives (I have no idea why this matters)

#### Two-way binding

A two-way binding looks the same as one-way except that it starts with `@=`.

```xml
<EditText android:text="@={mainViewModel.result}" />
```

#### Event and listener bindings

Functions can be bound as _event bindings_ using the `::` operator.

```xml
android::onClick="@{uiController::convertCurrency}"
```

Or, better yet, you can use a _listener binding_ which accepts a lambda and therefore enables the use of parameters.

```xml
android::onClick="@{() -> viewModel.setAmount(viewModel.result)}
```

# Room Persistence

## [DAOs](https://developer.android.com/training/data-storage/room/accessing-data)

 Room creates each DAO implementation at compile time.

 Room doesn't support database access on the main thread by default. Asynchronous queries that return instances of LiveData or Flowable are exempt from this rule because they asynchronously run the query on a background thread when needed.

## [ViewModels](https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#8)

Do not put database calls in a `ViewModel`.
