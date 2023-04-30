using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

namespace app
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }
        MySqlConnection conn = new MySqlConnection("Server=localhost;database=oe_univ;UID=root;password=;SslMode=none");
        public string fname;
        public string lname;
        public string email;
        public string token;

        private void button1_Click(object sender, EventArgs e)
        {

            try
            {
                conn.Open();
                MySqlCommand cmd = new MySqlCommand("SELECT * FROM `users` WHERE email=@email AND password = @password" , conn);
                cmd.Parameters.AddWithValue("@email", username.Text);
                cmd.Parameters.AddWithValue("@password", password.Text);
                MySqlDataReader reader = cmd.ExecuteReader();
                if(reader.HasRows)
                {
                    while(reader.Read())
                    {

                        if (reader["email"].ToString() == username.Text)
                        {
                            if (reader["password"].ToString() == password.Text && reader["active"].ToString() == "1")
                            {
                                 fname = reader["firstname"].ToString();
                                 lname = reader["lastname"].ToString();
                                Form2 form2 = new Form2();
                                form2.fname = fname;
                                form2.lname = lname;
                                form2.email = reader["email"].ToString();
                                form2.token = reader["token"].ToString();
                                form2.Show();
                                this.Hide();
                            }
                            else
                            {
                                MessageBox.Show("password is not coorect");
                            }
                        }
                        else
                        {
                            MessageBox.Show("email not found");
                        }
                    }
                }
                conn.Close();
            }
            catch (Exception)
            {
                MessageBox.Show("error in connection");
            }

          

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            password.UseSystemPasswordChar = true;
           
        }

        private void password_KeyPress(object sender, KeyPressEventArgs e)
        {
            if(e.KeyChar==(char)Keys.Enter)
            {
                try
                {
                    conn.Open();
                    MySqlCommand cmd = new MySqlCommand("SELECT * FROM `users` WHERE email=@email AND password = @password", conn);
                    cmd.Parameters.AddWithValue("@email", username.Text);
                    cmd.Parameters.AddWithValue("@password", password.Text);
                    MySqlDataReader reader = cmd.ExecuteReader();
                    if (reader.HasRows)
                    {
                        while (reader.Read())
                        {

                            if (reader["email"].ToString() == username.Text)
                            {
                                if (reader["password"].ToString() == password.Text && reader["active"].ToString() == "1")
                                {
                                    string fname = reader["firstname"].ToString();
                                    string lname = reader["lastname"].ToString();
                                    Form2 form2 = new Form2();
                                    form2.fname = fname;
                                    form2.lname = lname;
                                    form2.email = reader["email"].ToString();
                                    form2.token = reader["token"].ToString();
                                    form2.Show();
                                    this.Hide();
                                }
                                else
                                {
                                    MessageBox.Show("password is not coorect");
                                }
                            }
                            else
                            {
                                MessageBox.Show("email not found");
                            }
                        }
                    }
                    conn.Close();
                }
                catch (Exception)
                {
                    MessageBox.Show("error in connection");
                }

            }
        }
    }
}
