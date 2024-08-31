using System;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace PageRu.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    [QueryProperty(nameof(ItemId), nameof(ItemId))]
    public partial class PagesAddingPage : ContentPage
    {
        public PagesAddingPage()
        {
            InitializeComponent();
            BindingContext = new Models.Page();
        }
        public string ItemId
        {
            set
            {
                LoadMan(value);
            }
        }
        private async void LoadMan(string value)
        {
            try
            {
                int id = Convert.ToInt32(value);
                Models.Page page = await App.PageDB.GetPageAsync(id);
                BindingContext = page;
            }
            catch { }
        }

        private async void Save_Clicked(object sender, EventArgs e)
        {
            Models.Page page = (Models.Page)BindingContext;
            page.Date = DateTime.Now;
            if (!string.IsNullOrWhiteSpace(page.Content) || !string.IsNullOrWhiteSpace(page.NamePage))
            {
                await App.PageDB.SavePageAsync(page);
            }

            await Shell.Current.GoToAsync("..");
        }
        private async void Delete_Clicked(object sender, EventArgs e)
        {
            Models.Page page = (Models.Page)BindingContext;
            await App.PageDB.DeletePageAsync(page);
            await Shell.Current.GoToAsync("..");
        }
    }
}