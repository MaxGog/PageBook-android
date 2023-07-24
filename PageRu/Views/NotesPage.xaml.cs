using System;
using System.Linq;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace PageRu.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class NotesPage : ContentPage
    {
        public NotesPage()
        {
            InitializeComponent();
        }
        protected override async void OnAppearing()
        {
            collectionView.ItemsSource = await App.PageDB.GetPagesAsync();
            base.OnAppearing();
        }

        private async void collectionView_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (e.CurrentSelection != null)
            {
                Models.Page page = (Models.Page)e.CurrentSelection.FirstOrDefault();
                await Shell.Current.GoToAsync($"{nameof(PagesAddingPage)}?{nameof(PagesAddingPage.ItemId)}={page.ID.ToString()}");
            }
        }
        private async void AddPage_Clicked(object sender, EventArgs e)
        {
            await Shell.Current.GoToAsync(nameof(PagesAddingPage));
        }
    }
}