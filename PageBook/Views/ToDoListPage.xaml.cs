using System.ComponentModel;
using PageBook.ViewModels;

namespace PageBook.Views;

public partial class ToDoListPage : ContentPage
{
    private ToDoListViewModel _viewModel;

    public ToDoListPage()
    {
        InitializeComponent();
        BindingContext = _viewModel = new ToDoListViewModel(Navigation);
        
        // Добавляем отслеживание загрузки
         _viewModel.PropertyChanged += (sender, e) => ViewModel_PropertyChanged(sender, e);
    }

    private void ViewModel_PropertyChanged(object sender, PropertyChangedEventArgs e)
    {
        if (e.PropertyName == nameof(ToDoListViewModel.IsLoading))
        {
            //IsLoading = _viewModel.IsLoading;
        }
    }
}