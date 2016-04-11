class Vendor < ActiveRecord::Base
  self.primary_key = 'VendorSN'

  has_many :products, :foreign_key => 'ProductVendor'
end
