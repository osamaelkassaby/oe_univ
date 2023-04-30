using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
namespace app
{
    public partial class Form2 : Form
    {
        //VARIABELS
        Dashboard dashboard = new Dashboard();
        Students students = new Students(); 
        MySqlConnection conn = new MySqlConnection("server=localhost;database=oe_univ;UID=root;password=;SslMode=none");
        public string fname;
        public string lname;
        public string email;
        public string token;

        public Form2()
        {
            InitializeComponent();
        }

        private void Form2_Load(object sender, EventArgs e)
        {

            panel.Controls.Add(dashboard);
            conn.Open();
            MySqlCommand cmd = new MySqlCommand("SELECT img FROM `users` WHERE token=@token", conn);
            cmd.Parameters.AddWithValue("@token", token);
            MySqlDataReader reader = cmd.ExecuteReader();
            if (reader.HasRows)
            {
                while (reader.Read())
                {
                    try
                    {
                         byte[] img = (byte[])reader["img"];
                    MemoryStream ms = new MemoryStream(img);
                    pictureBox1.Image = Image.FromStream(ms);
                    }catch { 

                    }
                   
                }
            }
            conn.Close();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Courses course = new Courses();
            panel.Controls.Clear();
            panel.Controls.Add(course);
        }

        private void btndash_Click(object sender, EventArgs e)
        {
            panel.Controls.Clear();
            panel.Controls.Add(dashboard);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            panel.Controls.Clear();
            panel.Controls.Add(students);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            Settings settings = new Settings();
            panel.Controls.Clear();
            settings.fname = fname;
            settings.lname= lname;
            settings.token= token;
            panel.Controls.Add(settings);
        }
    }
}
