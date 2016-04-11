class CreateVendors < ActiveRecord::Migration
  def change
    create_table :vendors, id: false do |t|
      t.primary_key :VendorSN
      t.string :VendorTitle

      t.timestamps null: false
    end

    add_index :vendors, :VendorSN, unique: true
  end
end
